package com.socmonitor.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.socmonitor.databinding.ActivityLoginBinding;
import com.socmonitor.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager session;

    // Demo credentials (replace with real API auth)
    private static final String DEMO_EMAIL    = "analyst@soc.local";
    private static final String DEMO_PASSWORD = "soc@2024";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);

        binding.btnLogin.setOnClickListener(v -> attemptLogin());

        // Pre-fill demo credentials hint
        binding.etEmail.setHint("analyst@soc.local");
        binding.etPassword.setHint("soc@2024");
    }

    private void attemptLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String pass  = binding.etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            binding.etPassword.setError("Password required");
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnLogin.setEnabled(false);

        // Simulate network delay
        binding.getRoot().postDelayed(() -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnLogin.setEnabled(true);

            if (email.equals(DEMO_EMAIL) && pass.equals(DEMO_PASSWORD)) {
                session.createLoginSession(email, "SOC Analyst");
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                binding.etPassword.setText("");
            }
        }, 1200);
    }
}
