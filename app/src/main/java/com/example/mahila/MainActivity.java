package com.example.mahila;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.mahila.api.TokenManager;
import com.example.mahila.ui.assistance.AssistanceFragment;
import com.example.mahila.ui.awareness.AwarenessFragment;
import com.example.mahila.ui.home.HomeFragment;
import com.example.mahila.ui.profile.ProfileFragment;
import com.example.mahila.ui.tracker.TrackerFragment;
import com.example.mahila.utils.LocaleHelper;
import com.example.mahila.utils.SQLiteToMySQLMigrator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tokenManager = new TokenManager(this);
        LocaleHelper.setLocale(this, tokenManager.getLanguage());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_tracker) {
                selectedFragment = new TrackerFragment();
            } else if (itemId == R.id.nav_assistance) {
                selectedFragment = new AssistanceFragment();
            } else if (itemId == R.id.nav_awareness) {
                selectedFragment = new AwarenessFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

        // Set default fragment to Home
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        // Trigger automatic migration check from SQLite to MySQL
        SQLiteToMySQLMigrator.checkAndMigrate(this, new SQLiteToMySQLMigrator.MigrationCallback() {
            @Override
            public void onSuccess() {
                // Migrated cleanly
            }

            @Override
            public void onFailure(String error) {
                // Handle offline or pending sync
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.drawer_settings || id == R.id.drawer_language) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.drawer_logout) {
            tokenManager.clear();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}