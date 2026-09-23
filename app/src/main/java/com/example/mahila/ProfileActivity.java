package com.example.mahila;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtName, txtEmail, txtPeriodDate, txtPrediction;
    private Button btnLastPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPeriodDate = findViewById(R.id.txtPeriodDate);
        txtPrediction = findViewById(R.id.txtPrediction);
        btnLastPeriod = findViewById(R.id.btnLastPeriod);

        AppPreferences prefs = new AppPreferences(this);

        txtName.setText(prefs.getUserName());
        txtEmail.setText(prefs.getUserEmail());

        btnLastPeriod.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog picker = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {

                        Calendar selected = Calendar.getInstance();
                        selected.set(year, month, dayOfMonth);

                        prefs.saveLastPeriodMillis(selected.getTimeInMillis());

                        SimpleDateFormat sdf =
                                new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                        txtPeriodDate.setText(
                                "Last Period: " + sdf.format(selected.getTime())
                        );

                        selected.add(Calendar.DAY_OF_MONTH, 28);

                        txtPrediction.setText(
                                "Next Period: " + sdf.format(selected.getTime())
                        );
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            picker.show();
        });
    }
}