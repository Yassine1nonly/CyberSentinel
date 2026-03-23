package com.socmonitor.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.socmonitor.R;
import com.socmonitor.model.Alert;
import com.socmonitor.ui.activities.AlertDetailActivity;
import com.socmonitor.utils.SeverityUtils;

import java.util.ArrayList;
import java.util.List;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.AlertViewHolder> {

    private final Context context;
    private List<Alert> alerts = new ArrayList<>();

    public AlertsAdapter(Context context) {
        this.context = context;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_alert, parent, false);
        return new AlertViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        Alert alert = alerts.get(position);

        holder.tvTitle.setText(alert.getTitle());
        holder.tvDescription.setText(alert.getDescription());
        holder.tvSeverity.setText(alert.getSeverity());
        holder.tvSeverity.setBackgroundColor(SeverityUtils.getColor(context, alert.getSeverity()));
        holder.tvHost.setText("🖥 " + alert.getHost());
        holder.tvCategory.setText("📂 " + alert.getCategory());
        holder.tvTimestamp.setText(alert.getTimestamp());
        holder.tvStatus.setText(alert.getStatus());

        // Status dot color
        switch (alert.getStatus().toUpperCase()) {
            case "NEW":          holder.tvStatus.setTextColor(0xFFEF5350); break;
            case "ACKNOWLEDGED": holder.tvStatus.setTextColor(0xFFFFB300); break;
            case "RESOLVED":     holder.tvStatus.setTextColor(0xFF66BB6A); break;
        }

        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, AlertDetailActivity.class);
            intent.putExtra(AlertDetailActivity.EXTRA_ALERT_ID, alert.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return alerts.size(); }

    static class AlertViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvTitle, tvDescription, tvSeverity, tvHost, tvCategory, tvTimestamp, tvStatus;

        AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            card          = itemView.findViewById(R.id.card);
            tvTitle       = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvSeverity    = itemView.findViewById(R.id.tv_severity);
            tvHost        = itemView.findViewById(R.id.tv_host);
            tvCategory    = itemView.findViewById(R.id.tv_category);
            tvTimestamp   = itemView.findViewById(R.id.tv_timestamp);
            tvStatus      = itemView.findViewById(R.id.tv_status);
        }
    }
}
