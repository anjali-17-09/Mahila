package com.example.mahila;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
/**
 * Simple local storage using SharedPreferences only.
 * Data is stored as plain text so it is easy to read and debug.
 */
public class AppPreferences {
    public static final int CYCLE_LENGTH_DAYS = 28;
    private static final String PREFS_NAME = "mahila_prefs";
    private static final String KEY_LAST_PERIOD = "last_period_millis";
    private static final String KEY_SYMPTOMS = "symptom_history";
    private static final String KEY_CONTACTS = "emergency_contacts";
    private static final String KEY_CLINICIAN_REMINDER = "clinician_reminder_millis";
    private static final String KEY_LAST_HEALTH_TIP = "last_health_tip";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String RECORD_SEP = "\n";
    private static final String FIELD_SEP = "|";
    private final SharedPreferences prefs;
    public AppPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void saveLastPeriodMillis(long millis) {
        prefs.edit().putLong(KEY_LAST_PERIOD, millis).apply();
    }
    public long getLastPeriodMillis() {
        return prefs.getLong(KEY_LAST_PERIOD, -1L);
    }
    public boolean hasLastPeriod() {
        return getLastPeriodMillis() > 0L;
    }
    public void addSymptomEntry(String entry) {
        String current = prefs.getString(KEY_SYMPTOMS, "");
        if (current.isEmpty()) {
            prefs.edit().putString(KEY_SYMPTOMS, entry).apply();
        } else {
            prefs.edit().putString(KEY_SYMPTOMS, entry + RECORD_SEP + current).apply();
        }
    }
    public String getSymptomHistoryText() {
        return prefs.getString(KEY_SYMPTOMS, "");
    }
    public String getLatestSymptomEntry() {
        String history = getSymptomHistoryText();
        if (history.isEmpty()) {
            return "";
        }
        int newline = history.indexOf(RECORD_SEP);
        return newline == -1 ? history : history.substring(0, newline);
    }
    public void saveClinicianReminder(long millis) {
        prefs.edit().putLong(KEY_CLINICIAN_REMINDER, millis).apply();
    }
    public long getClinicianReminderMillis() {
        return prefs.getLong(KEY_CLINICIAN_REMINDER, -1L);
    }
    public boolean hasClinicianReminder() {
        return getClinicianReminderMillis() > 0L;
    }
    public void saveLastHealthTip(String title) {
        prefs.edit().putString(KEY_LAST_HEALTH_TIP, title).apply();
    }
    public String getLastHealthTip() {
        return prefs.getString(KEY_LAST_HEALTH_TIP, "");
    }
    public List<EmergencyContact> getContacts() {
        List<EmergencyContact> contacts = new ArrayList<>();
        String saved = prefs.getString(KEY_CONTACTS, "");
        if (saved.isEmpty()) {
            return contacts;
        }
        String[] lines = saved.split(RECORD_SEP, -1);
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\" + FIELD_SEP, -1);
            if (parts.length >= 3) {
                contacts.add(new EmergencyContact(parts[0], parts[1], parts[2]));
            }
        }
        return contacts;
    }
    public void saveContacts(List<EmergencyContact> contacts) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < contacts.size(); i++) {
            EmergencyContact contact = contacts.get(i);
            if (i > 0) {
                builder.append(RECORD_SEP);
            }
            builder.append(clean(contact.id))
                    .append(FIELD_SEP)
                    .append(clean(contact.name))
                    .append(FIELD_SEP)
                    .append(clean(contact.phone));
        }
        prefs.edit().putString(KEY_CONTACTS, builder.toString()).apply();
    }
    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(FIELD_SEP, " ").replace(RECORD_SEP, " ").trim();
    }
    public static class EmergencyContact {
        public final String id;
        public String name;
        public String phone;
        public EmergencyContact(String id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }
    }
    public void saveUser(String name, String email, String password) {
        prefs.edit()
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_PASSWORD, password)
                .apply();
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, "");
    }

    public String getUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, "");
    }

    public String getUserPassword() {
        return prefs.getString(KEY_USER_PASSWORD, "");
    }

    public void setLoggedIn(boolean loggedIn) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, loggedIn).apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}