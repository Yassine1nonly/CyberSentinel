package com.socmonitor.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.socmonitor.R;
import com.socmonitor.network.MockDataRepository;
import com.socmonitor.ui.fragments.AlertsFragment;
import com.socmonitor.ui.fragments.DashboardFragment;
import com.socmonitor.ui.fragments.IpsFragment;
import com.socmonitor.ui.fragments.SettingsFragment;
import com.socmonitor.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // No setSupportActionBar — theme handles the ActionBar
        BottomNavigationView nav = findViewById(R.id.bottom_nav);

        nav.setOnNavigationItemSelectedListener(item -> {
            Fragment f = null;
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                f = new DashboardFragment();
                setTitle("Dashboard");
            } else if (id == R.id.nav_alerts) {
                f = new AlertsFragment();
                setTitle("Alerts");
            } else if (id == R.id.nav_ips) {
                f = new IpsFragment();
                setTitle("Suspicious IPs");
            } else if (id == R.id.nav_settings) {
                f = new SettingsFragment();
                setTitle("Settings");
            }
            if (f != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, f).commit();
                return true;
            }
            return false;
        });

        nav.setSelectedItemId(R.id.nav_dashboard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            new SessionManager(this).logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
