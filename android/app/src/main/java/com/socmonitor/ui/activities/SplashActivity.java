package com.socmonitor.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.socmonitor.R;
import com.socmonitor.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Class<?> dest = new SessionManager(this).isLoggedIn()
                    ? MainActivity.class : LoginActivity.class;
            startActivity(new Intent(this, dest));
            finish();
        }, 1500);
    }
}
