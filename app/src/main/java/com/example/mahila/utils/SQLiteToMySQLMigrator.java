package com.example.mahila.utils;

import android.content.Context;
import android.database.Cursor;

import com.example.mahila.DatabaseHelper;
import com.example.mahila.api.ApiClient;
import com.example.mahila.api.ApiService;
import com.example.mahila.api.TokenManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SQLiteToMySQLMigrator {

    public interface MigrationCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public static void checkAndMigrate(Context context, MigrationCallback callback) {
        TokenManager tokenManager = new TokenManager(context);
        if (tokenManager.isMigrated()) {
            if (callback != null) callback.onSuccess();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        String email = tokenManager.getUserEmail();
        if (email == null || email.isEmpty()) {
            if (callback != null) callback.onSuccess();
            return;
        }

        try {
            JSONObject payload = new JSONObject();
            payload.put("email", email);
            payload.put("name", tokenManager.getUserName());

            // Extract Periods
            JSONArray periodsArray = new JSONArray();
            Cursor pCursor = dbHelper.getPeriod(email);
            if (pCursor != null && pCursor.moveToFirst()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                do {
                    int dateIdx = pCursor.getColumnIndex(DatabaseHelper.KEY_PERIOD_DATE);
                    if (dateIdx >= 0) {
                        long dateMillis = pCursor.getLong(dateIdx);
                        String dateStr = sdf.format(new Date(dateMillis));
                        JSONObject pObj = new JSONObject();
                        pObj.put("startDate", dateStr);
                        pObj.put("flowLevel", "MEDIUM");
                        pObj.put("painScale", 3);
                        periodsArray.put(pObj);
                    }
                } while (pCursor.moveToNext());
                pCursor.close();
            }
            payload.put("periods", periodsArray);

            // Extract Symptoms
            JSONArray symptomsArray = new JSONArray();
            Cursor sCursor = dbHelper.getSymptoms(email);
            if (sCursor != null && sCursor.moveToFirst()) {
                do {
                    int nameIdx = sCursor.getColumnIndex(DatabaseHelper.KEY_SYMPTOM);
                    int dateIdx = sCursor.getColumnIndex(DatabaseHelper.KEY_CREATED_AT);
                    String name = nameIdx >= 0 ? sCursor.getString(nameIdx) : "Cramps";
                    String date = dateIdx >= 0 ? sCursor.getString(dateIdx) : new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    JSONObject sObj = new JSONObject();
                    sObj.put("symptomName", name);
                    sObj.put("severity", "MODERATE");
                    sObj.put("loggedDate", date);
                    symptomsArray.put(sObj);
                } while (sCursor.moveToNext());
                sCursor.close();
            }
            payload.put("symptoms", symptomsArray);

            // Extract Contacts
            JSONArray contactsArray = new JSONArray();
            Cursor cCursor = dbHelper.getContacts(email);
            if (cCursor != null && cCursor.moveToFirst()) {
                do {
                    int nameIdx = cCursor.getColumnIndex(DatabaseHelper.KEY_CONTACT_NAME);
                    int phoneIdx = cCursor.getColumnIndex(DatabaseHelper.KEY_CONTACT_PHONE);
                    String name = nameIdx >= 0 ? cCursor.getString(nameIdx) : "Contact";
                    String phone = phoneIdx >= 0 ? cCursor.getString(phoneIdx) : "";
                    JSONObject cObj = new JSONObject();
                    cObj.put("contactName", name);
                    cObj.put("phoneNumber", phone);
                    cObj.put("category", "OTHER");
                    cObj.put("isFavorite", false);
                    contactsArray.put(cObj);
                } while (cCursor.moveToNext());
                cCursor.close();
            }
            payload.put("contacts", contactsArray);

            ApiClient.postRequest(context, ApiService.MIGRATION_SYNC, payload, new ApiClient.ApiCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    tokenManager.setMigrated(true);
                    if (callback != null) callback.onSuccess();
                }

                @Override
                public void onError(String message) {
                    if (callback != null) callback.onFailure(message);
                }
            });

        } catch (Exception e) {
            if (callback != null) callback.onFailure(e.getMessage());
        }
    }
}
