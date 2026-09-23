package com.example.mahila;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private Button btnContinue;
    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        appPreferences = new AppPreferences(this);

        txtWelcome = findViewById(R.id.txtWelcome);
        btnContinue = findViewById(R.id.btnContinue);

        String name = appPreferences.getUserName();

        if (!name.isEmpty()) {
            txtWelcome.setText("Welcome, " + name + " 👋");
        }

        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            finish();
        });
    }
}