package com.example.mahila;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mahila.db";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_PERIODS = "periods";
    public static final String TABLE_SYMPTOMS = "symptoms";
    public static final String TABLE_CONTACTS = "contacts";

    // Common Columns
    public static final String KEY_ID = "id";
    public static final String KEY_USER_EMAIL = "user_email";

    // Users Table Columns
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_AGE = "age";

    // Periods Table Columns
    public static final String KEY_PERIOD_DATE = "period_date";

    // Symptoms Table Columns
    public static final String KEY_SYMPTOM = "symptom";
    public static final String KEY_CREATED_AT = "created_at";

    // Contacts Table Columns
    public static final String KEY_CONTACT_NAME = "contact_name";
    public static final String KEY_CONTACT_PHONE = "contact_phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_EMAIL + " TEXT UNIQUE, "
                + KEY_PASSWORD + " TEXT, "
                + KEY_AGE + " INTEGER DEFAULT 0" + ")";

        String CREATE_PERIODS_TABLE = "CREATE TABLE " + TABLE_PERIODS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USER_EMAIL + " TEXT, "
                + KEY_PERIOD_DATE + " INTEGER" + ")";

        String CREATE_SYMPTOMS_TABLE = "CREATE TABLE " + TABLE_SYMPTOMS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USER_EMAIL + " TEXT, "
                + KEY_SYMPTOM + " TEXT, "
                + KEY_CREATED_AT + " TEXT" + ")";

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USER_EMAIL + " TEXT, "
                + KEY_CONTACT_NAME + " TEXT, "
                + KEY_CONTACT_PHONE + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PERIODS_TABLE);
        db.execSQL(CREATE_SYMPTOMS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + KEY_AGE + " INTEGER DEFAULT 0");
        } else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIODS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYMPTOMS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }
    }

    // Insert User
    public boolean insertUser(String name, String email, String password) {
        return insertUser(name, email, password, 0);
    }

    // Insert User with Age
    public boolean insertUser(String name, String email, String password, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_AGE, age);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Update User Age
    public boolean updateUserAge(String email, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AGE, age);
        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = {email};

        int count = db.update(TABLE_USERS, values, selection, selectionArgs);
        return count > 0;
    }

    // Update User Profile (Name and Age)
    public boolean updateUser(String email, String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_AGE, age);
        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = {email};

        int count = db.update(TABLE_USERS, values, selection, selectionArgs);
        return count > 0;
    }

    // Check Login
    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID};
        String selection = KEY_EMAIL + " = ? AND " + KEY_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    // Get User Details
    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = {email};

        return db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
    }

    // Save Period Date
    public boolean savePeriod(String userEmail, long periodDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_EMAIL, userEmail);
        values.put(KEY_PERIOD_DATE, periodDate);

        long result = db.insert(TABLE_PERIODS, null, values);
        return result != -1;
    }

    // Get Period Records
    public Cursor getPeriod(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = KEY_USER_EMAIL + " = ?";
        String[] selectionArgs = {userEmail};

        return db.query(TABLE_PERIODS, null, selection, selectionArgs, null, null, KEY_PERIOD_DATE + " DESC");
    }

    // Save Symptom
    public boolean saveSymptom(String userEmail, String symptom, String createdAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_EMAIL, userEmail);
        values.put(KEY_SYMPTOM, symptom);
        values.put(KEY_CREATED_AT, createdAt);

        long result = db.insert(TABLE_SYMPTOMS, null, values);
        return result != -1;
    }

    // Get Symptoms
    public Cursor getSymptoms(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = KEY_USER_EMAIL + " = ?";
        String[] selectionArgs = {userEmail};

        return db.query(TABLE_SYMPTOMS, null, selection, selectionArgs, null, null, KEY_ID + " DESC");
    }

    // Save Contact
    public boolean saveContact(String userEmail, String contactName, String contactPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_EMAIL, userEmail);
        values.put(KEY_CONTACT_NAME, contactName);
        values.put(KEY_CONTACT_PHONE, contactPhone);

        long result = db.insert(TABLE_CONTACTS, null, values);
        return result != -1;
    }

    // Get Contacts
    public Cursor getContacts(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = KEY_USER_EMAIL + " = ?";
        String[] selectionArgs = {userEmail};

        return db.query(TABLE_CONTACTS, null, selection, selectionArgs, null, null, KEY_ID + " DESC");
    }
}


