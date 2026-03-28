package com.socmonitor.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.socmonitor.R;
import com.socmonitor.model.Alert;
import com.socmonitor.network.MockDataRepository;
import com.socmonitor.utils.SeverityUtils;

public class AlertDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "alert_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Alert Detail");
        }

        String id = getIntent().getStringExtra(EXTRA_ID);
        Alert alert = MockDataRepository.getInstance().getById(id);
        if (alert == null) { finish(); return; }

        bind(alert);
    }

    private void bind(Alert a) {
        setText(R.id.tv_alert_id,    "ID: " + a.getId());
        setText(R.id.tv_alert_title, a.getTitle());
        setText(R.id.tv_description, a.getDescription());
        setText(R.id.tv_timestamp,   a.getTimestamp());
        setText(R.id.tv_src_ip,      a.getSourceIp());
        setText(R.id.tv_dst_ip,      a.getDestinationIp());
        setText(R.id.tv_host,        a.getHost());
        setText(R.id.tv_category,    a.getCategory());
        setText(R.id.tv_rule_name,   a.getRuleName());
        setText(R.id.tv_mitre,       a.getMitreTechnique());

        TextView tvSev    = findViewById(R.id.tv_severity);
        TextView tvStatus = findViewById(R.id.tv_status);
        tvSev.setText(a.getSeverity());
        tvSev.setBackgroundColor(SeverityUtils.getColor(a.getSeverity()));
        tvStatus.setText(a.getStatus());
        tvStatus.setTextColor(SeverityUtils.getStatusColor(a.getStatus()));

        // Evidence
        if (a.getEvidence() != null) {
            StringBuilder sb = new StringBuilder();
            for (String e : a.getEvidence()) sb.append("• ").append(e).append("\n");
            setText(R.id.tv_evidence, sb.toString().trim());
        }

        Button btnAck     = findViewById(R.id.btn_acknowledge);
        Button btnResolve = findViewById(R.id.btn_resolve);
        updateButtons(a, btnAck, btnResolve, tvStatus);

        btnAck.setOnClickListener(v -> {
            MockDataRepository.getInstance().acknowledge(a.getId());
            a.setStatus("ACKNOWLEDGED");
            updateButtons(a, btnAck, btnResolve, tvStatus);
            Toast.makeText(this, "Alert acknowledged", Toast.LENGTH_SHORT).show();
        });

        btnResolve.setOnClickListener(v -> {
            MockDataRepository.getInstance().resolve(a.getId());
            a.setStatus("RESOLVED");
            updateButtons(a, btnAck, btnResolve, tvStatus);
            Toast.makeText(this, "Alert resolved \u2713", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateButtons(Alert a, Button ack, Button resolve, TextView tvStatus) {
        tvStatus.setText(a.getStatus());
        tvStatus.setTextColor(SeverityUtils.getStatusColor(a.getStatus()));
        boolean isAck      = "ACKNOWLEDGED".equals(a.getStatus());
        boolean isResolved = "RESOLVED".equals(a.getStatus());
        ack.setEnabled(!isAck && !isResolved);
        ack.setAlpha((!isAck && !isResolved) ? 1f : 0.4f);
        resolve.setEnabled(!isResolved);
        resolve.setAlpha(!isResolved ? 1f : 0.4f);
    }

    private void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (tv != null) tv.setText(text);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
