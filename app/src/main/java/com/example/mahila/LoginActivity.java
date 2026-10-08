package com.example.mahila;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mahila.api.ApiClient;
import com.example.mahila.api.ApiService;
import com.example.mahila.api.TokenManager;
import org.json.JSONObject;

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

            try {
                JSONObject json = new JSONObject();
                json.put("email", email);
                json.put("password", password);

                ApiClient.postRequest(this, ApiService.AUTH_LOGIN, json, new ApiClient.ApiCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            String token = obj.optString("token");
                            Long userId = obj.optLong("userId", 1L);
                            String name = obj.optString("name", "User");
                            String lang = obj.optString("preferredLanguage", "en");

                            new TokenManager(LoginActivity.this).saveSession(token, userId, email, name, lang);
                            appPreferences.setLoggedIn(true);
                            appPreferences.setCurrentUserEmail(email);

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } catch (Exception e) {
                            fallbackLogin(email, password);
                        }
                    }

                    @Override
                    public void onError(String message) {
                        fallbackLogin(email, password);
                    }
                });
            } catch (Exception e) {
                fallbackLogin(email, password);
            }
        });

        btnGuest.setOnClickListener(v -> {
            appPreferences.setLoggedIn(false);
            appPreferences.clearCurrentUserEmail();
            startActivity(new Intent(this, MainActivity.class));
        });

        txtSignup.setOnClickListener(v -> startActivity(new Intent(this, SignupActivity.class)));
    }

    private void fallbackLogin(String email, String password) {
        if (dbHelper.checkLogin(email, password)) {
            appPreferences.setLoggedIn(true);
            appPreferences.setCurrentUserEmail(email);
            new TokenManager(LoginActivity.this).saveSession("local_token", 1L, email, "User", "en");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}