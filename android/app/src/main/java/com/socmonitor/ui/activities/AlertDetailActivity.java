package com.socmonitor.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.socmonitor.R;
import com.socmonitor.databinding.ActivityAlertDetailBinding;
import com.socmonitor.model.Alert;
import com.socmonitor.network.MockDataRepository;
import com.socmonitor.utils.SeverityUtils;

public class AlertDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ALERT_ID = "alert_id";
    private ActivityAlertDetailBinding binding;
    private Alert alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlertDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Alert Detail");
        }

        String alertId = getIntent().getStringExtra(EXTRA_ALERT_ID);
        alert = MockDataRepository.getInstance().getAlertById(alertId);

        if (alert == null) {
            finish();
            return;
        }

        populateUI();
        setupButtons();
    }

    private void populateUI() {
        binding.tvAlertTitle.setText(alert.getTitle());
        binding.tvDescription.setText(alert.getDescription());
        binding.tvSeverity.setText(alert.getSeverity());
        binding.tvStatus.setText(alert.getStatus());
        binding.tvSrcIp.setText(alert.getSourceIp());
        binding.tvDstIp.setText(alert.getDestinationIp());
        binding.tvHost.setText(alert.getHost());
        binding.tvCategory.setText(alert.getCategory());
        binding.tvTimestamp.setText(alert.getTimestamp());
        binding.tvRuleName.setText(alert.getRuleName());
        binding.tvMitre.setText(alert.getMitreTechnique());
        binding.tvAlertId.setText("ID: " + alert.getId());

        // Severity chip color
        binding.tvSeverity.setBackgroundColor(SeverityUtils.getColor(this, alert.getSeverity()));

        // Evidence list
        if (alert.getEvidence() != null && !alert.getEvidence().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String e : alert.getEvidence()) sb.append("• ").append(e).append("\n");
            binding.tvEvidence.setText(sb.toString().trim());
        } else {
            binding.tvEvidence.setText("No evidence attached.");
        }

        updateButtonState();
    }

    private void setupButtons() {
        binding.btnAcknowledge.setOnClickListener(v -> {
            MockDataRepository.getInstance().acknowledgeAlert(alert.getId());
            alert.setStatus("ACKNOWLEDGED");
            updateButtonState();
            Toast.makeText(this, "Alert acknowledged", Toast.LENGTH_SHORT).show();
        });

        binding.btnResolve.setOnClickListener(v -> {
            MockDataRepository.getInstance().resolveAlert(alert.getId());
            alert.setStatus("RESOLVED");
            updateButtonState();
            Toast.makeText(this, "Alert resolved ✓", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateButtonState() {
        String status = alert.getStatus();
        binding.tvStatus.setText(status);

        switch (status.toUpperCase()) {
            case "ACKNOWLEDGED":
                binding.btnAcknowledge.setEnabled(false);
                binding.btnAcknowledge.setAlpha(0.5f);
                break;
            case "RESOLVED":
                binding.btnAcknowledge.setEnabled(false);
                binding.btnAcknowledge.setAlpha(0.5f);
                binding.btnResolve.setEnabled(false);
                binding.btnResolve.setAlpha(0.5f);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
