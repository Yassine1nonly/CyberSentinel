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

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.VH> {

    private final Context ctx;
    private List<Alert> alerts = new ArrayList<>();

    public AlertsAdapter(Context ctx) { this.ctx = ctx; }

    public void setAlerts(List<Alert> list) {
        this.alerts = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_alert, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Alert a = alerts.get(pos);
        h.tvTitle.setText(a.getTitle());
        h.tvDesc.setText(a.getDescription());
        h.tvSeverity.setText(a.getSeverity());
        h.tvSeverity.setBackgroundColor(SeverityUtils.getColor(a.getSeverity()));
        h.tvHost.setText(a.getHost());
        h.tvTs.setText(a.getTimestamp());
        h.tvStatus.setText(a.getStatus());
        h.tvStatus.setTextColor(SeverityUtils.getStatusColor(a.getStatus()));
        h.card.setOnClickListener(v -> {
            Intent i = new Intent(ctx, AlertDetailActivity.class);
            i.putExtra(AlertDetailActivity.EXTRA_ID, a.getId());
            ctx.startActivity(i);
        });
    }

    @Override public int getItemCount() { return alerts.size(); }

    static class VH extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvTitle, tvDesc, tvSeverity, tvHost, tvTs, tvStatus;
        VH(@NonNull View v) {
            super(v);
            card       = v.findViewById(R.id.card);
            tvTitle    = v.findViewById(R.id.tv_title);
            tvDesc     = v.findViewById(R.id.tv_description);
            tvSeverity = v.findViewById(R.id.tv_severity);
            tvHost     = v.findViewById(R.id.tv_host);
            tvTs       = v.findViewById(R.id.tv_timestamp);
            tvStatus   = v.findViewById(R.id.tv_status);
        }
    }
}
