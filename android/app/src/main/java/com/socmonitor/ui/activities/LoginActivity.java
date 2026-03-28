package com.socmonitor.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.socmonitor.R;
import com.socmonitor.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private static final String DEMO_EMAIL = "analyst@soc.local";
    private static final String DEMO_PASS  = "soc@2024";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etEmail    = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);
        Button   btnLogin   = findViewById(R.id.btn_login);
        ProgressBar pb      = findViewById(R.id.progress_bar);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass  = etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)) { etEmail.setError("Required"); return; }
            if (TextUtils.isEmpty(pass))  { etPassword.setError("Required"); return; }

            pb.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);

            btnLogin.postDelayed(() -> {
                pb.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
                if (email.equals(DEMO_EMAIL) && pass.equals(DEMO_PASS)) {
                    new SessionManager(this).login(email, "SOC Analyst");
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                }
            }, 1000);
        });
    }
}
