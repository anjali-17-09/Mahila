package com.example.mahila;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedicalAssistanceActivity extends AppCompatActivity {

    private static final String INTRO_TEXT =
            "Find care resources. This is not a substitute for professional medical advice.";
    private static final String CLINICIAN_DETAIL =
            "Book a consultation or visit a nearby clinic for personalized care.";

    private AppPreferences preferences;
    private TextView introText;
    private TextView clinicianDetailText;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medical_assistance);

        View root = findViewById(R.id.medical_assistance_root);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.medical_assistance_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Medical Assistance");
        }
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        preferences = new AppPreferences(this);
        introText = findViewById(R.id.text_medical_intro);
        clinicianDetailText = findViewById(R.id.text_clinician_detail);

        MaterialCardView clinicianCard = findViewById(R.id.card_talk_to_clinician);
        clinicianCard.setOnClickListener(v -> saveClinicianReminder());

        MaterialCardView urgentCard = findViewById(R.id.card_urgent_care);
        urgentCard.setOnClickListener(v -> {
            startActivity(new Intent(this, EmergencyContactsActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showSavedInfo();
    }

    private void saveClinicianReminder() {
        preferences.saveClinicianReminder(System.currentTimeMillis());
        showSavedInfo();
        Toast.makeText(this, "Reminder saved. Visit a clinic when you can.", Toast.LENGTH_SHORT).show();
    }

    private void showSavedInfo() {
        StringBuilder intro = new StringBuilder(INTRO_TEXT);
        if (preferences.hasLastPeriod()) {
            intro.append("\n\nLast period start: ")
                    .append(dateFormat.format(new Date(preferences.getLastPeriodMillis())));
        }
        String latestSymptom = preferences.getLatestSymptomEntry();
        if (!latestSymptom.isEmpty()) {
            intro.append("\nLatest symptoms: ").append(latestSymptom);
        }
        introText.setText(intro.toString());

        if (preferences.hasClinicianReminder()) {
            clinicianDetailText.setText(CLINICIAN_DETAIL + " Reminder saved on "
                    + dateFormat.format(new Date(preferences.getClinicianReminderMillis())) + ".");
        } else {
            clinicianDetailText.setText(CLINICIAN_DETAIL);
        }
    }
}
