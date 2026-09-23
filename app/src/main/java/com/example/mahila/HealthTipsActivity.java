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

public class HealthTipsActivity extends AppCompatActivity {

    private AppPreferences preferences;
    private TextView hydratedTitle;
    private TextView movementTitle;
    private TextView restTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_tips);

        View root = findViewById(R.id.health_tips_root);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.health_tips_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Health Tips");
        }
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        preferences = new AppPreferences(this);
        hydratedTitle = findViewById(R.id.text_tip_hydrated_title);
        movementTitle = findViewById(R.id.text_tip_movement_title);
        restTitle = findViewById(R.id.text_tip_rest_title);

        findViewById(R.id.card_tip_hydrated).setOnClickListener(v -> saveTip("Stay hydrated"));
        findViewById(R.id.card_tip_movement).setOnClickListener(v -> saveTip("Gentle movement"));
        findViewById(R.id.card_tip_rest).setOnClickListener(v -> saveTip("Rest when you need it"));

        showLastOpenedTip();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLastOpenedTip();
    }

    private void saveTip(String title) {
        preferences.saveLastHealthTip(title);
        showLastOpenedTip();
        Toast.makeText(this, "Saved \"" + title + "\" as your last opened tip", Toast.LENGTH_SHORT).show();
    }

    private void showLastOpenedTip() {
        String lastTip = preferences.getLastHealthTip();
        hydratedTitle.setText(labelFor("Stay hydrated", lastTip));
        movementTitle.setText(labelFor("Gentle movement", lastTip));
        restTitle.setText(labelFor("Rest when you need it", lastTip));
    }

    private String labelFor(String title, String lastTip) {
        if (title.equals(lastTip)) {
            return title + " (last opened)";
        }
        return title;
    }
}
