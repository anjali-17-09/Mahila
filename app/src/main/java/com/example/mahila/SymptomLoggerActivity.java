package com.example.mahila;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SymptomLoggerActivity extends AppCompatActivity {

    private AppPreferences preferences;
    private ChipGroup chipGroup;
    private TextView historyText;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_symptom_logger);

        View root = findViewById(R.id.symptom_logger_root);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.symptom_logger_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Symptom Logger");
        }
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        preferences = new AppPreferences(this);
        chipGroup = findViewById(R.id.symptom_chip_group);
        historyText = findViewById(R.id.text_symptom_history);

        MaterialButton saveButton = findViewById(R.id.button_save_symptoms);
        saveButton.setOnClickListener(v -> saveSelectedSymptoms());
    }

    @Override
    protected void onResume() {
        super.onResume();
        showHistory();
    }

    private void saveSelectedSymptoms() {
        List<Integer> checkedIds = chipGroup.getCheckedChipIds();
        if (checkedIds.isEmpty()) {
            Toast.makeText(this, "Select at least one symptom", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> names = new ArrayList<>();
        for (int chipId : checkedIds) {
            Chip chip = chipGroup.findViewById(chipId);
            if (chip == null) {
                continue;
            }
            names.add(chip.getText().toString());
        }
        if (names.isEmpty()) {
            Toast.makeText(this, "Select at least one symptom", Toast.LENGTH_SHORT).show();
            return;
        }

        String entry = dateFormat.format(new Date()) + " — " + String.join(", ", names);
        preferences.addSymptomEntry(entry);
        chipGroup.clearCheck();
        showHistory();
        Toast.makeText(this, "Symptoms saved", Toast.LENGTH_SHORT).show();
    }

    private void showHistory() {
        String history = preferences.getSymptomHistoryText();
        if (history.isEmpty()) {
            historyText.setText("No symptoms saved yet.");
        } else {
            historyText.setText(history);
        }
    }
}
