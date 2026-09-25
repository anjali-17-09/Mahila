package com.example.mahila;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private Button btnContinue;
    private AppPreferences appPreferences;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        appPreferences = new AppPreferences(this);
        dbHelper = new DatabaseHelper(this);

        txtWelcome = findViewById(R.id.txtWelcome);
        btnContinue = findViewById(R.id.btnContinue);

        String currentEmail = appPreferences.getCurrentUserEmail();
        String name = "";

        if (!currentEmail.isEmpty()) {
            Cursor cursor = dbHelper.getUser(currentEmail);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_NAME);
                    if (nameIndex != -1) {
                        name = cursor.getString(nameIndex);
                    }
                }
                cursor.close();
            }
        }

        if (!name.isEmpty()) {
            txtWelcome.setText("Welcome, " + name + " 👋");
        } else {
            txtWelcome.setText("Welcome Guest! 👋");
        }

        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            finish();
        });
    }
}