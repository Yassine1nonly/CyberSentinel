package com.socmonitor.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.badge.BadgeDrawable;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView nav = findViewById(R.id.bottom_nav);

        // New-alert badge
        int newCount = MockDataRepository.getInstance().filterBySeverity("ALL").size();
        BadgeDrawable badge = nav.getOrCreateBadge(R.id.nav_alerts);
        badge.setNumber(newCount);
        badge.setVisible(true);

        nav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment f = null;
            String title = "CyberSentinel";
            if (id == R.id.nav_dashboard) { f = new DashboardFragment(); title = "Dashboard"; }
            else if (id == R.id.nav_alerts)   { f = new AlertsFragment();   title = "Alerts"; }
            else if (id == R.id.nav_ips)      { f = new IpsFragment();      title = "Suspicious IPs"; }
            else if (id == R.id.nav_settings) { f = new SettingsFragment(); title = "Settings"; }
            if (f != null) {
                if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, f).commit();
                return true;
            }
            return false;
        });

        // Default screen
        nav.setSelectedItemId(R.id.nav_dashboard);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
