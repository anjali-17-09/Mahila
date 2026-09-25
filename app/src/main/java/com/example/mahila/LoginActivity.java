package com.example.mahila;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGuest;
    private TextView txtSignup;
    private AppPreferences appPreferences;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appPreferences = new AppPreferences(this);
        dbHelper = new DatabaseHelper(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnGuest = findViewById(R.id.btnGuest);
        txtSignup = findViewById(R.id.txtSignup);

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkLogin(email, password)) {

                appPreferences.setLoggedIn(true);
                appPreferences.setCurrentUserEmail(email);

                startActivity(new Intent(this, WelcomeActivity.class));
                finish();

            } else {
                Toast.makeText(this,
                        "Invalid email or password",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnGuest.setOnClickListener(v -> {
            appPreferences.setLoggedIn(false);
            appPreferences.clearCurrentUserEmail();
            startActivity(new Intent(this, HomeActivity.class));
        });

        txtSignup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
    }
}