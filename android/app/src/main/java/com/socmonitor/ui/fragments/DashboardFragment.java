package com.socmonitor.ui.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.socmonitor.R;
import com.socmonitor.model.DashboardStats;
import com.socmonitor.network.MockDataRepository;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load(view);
        SwipeRefreshLayout srl = view.findViewById(R.id.swipe_refresh);
        srl.setOnRefreshListener(() -> { load(view); srl.setRefreshing(false); });
    }

    private void load(View v) {
        DashboardStats s = MockDataRepository.getInstance().getStats();

        set(v, R.id.tv_total_alerts,        String.valueOf(s.totalAlerts));
        set(v, R.id.tv_critical,            String.valueOf(s.criticalCount));
        set(v, R.id.tv_high,                String.valueOf(s.highCount));
        set(v, R.id.tv_medium,              String.valueOf(s.mediumCount));
        set(v, R.id.tv_low,                 String.valueOf(s.lowCount));
        set(v, R.id.tv_failed_logins,       String.valueOf(s.failedLogins24h));
        set(v, R.id.tv_malware_detections,  String.valueOf(s.malwareDetections24h));
        set(v, R.id.tv_suspicious_ips,      String.valueOf(s.suspiciousIpsCount));
        set(v, R.id.tv_last_updated,        "Updated: " + s.lastUpdated);

        TextView tvThreat = v.findViewById(R.id.tv_threat_level);
        tvThreat.setText("Threat Level: " + s.threatLevel);
        switch (s.threatLevel) {
            case "CRITICAL": tvThreat.setBackgroundColor(Color.parseColor("#B71C1C")); break;
            case "HIGH":     tvThreat.setBackgroundColor(Color.parseColor("#E65100")); break;
            default:         tvThreat.setBackgroundColor(Color.parseColor("#1565C0")); break;
        }

        // Update the custom bar chart
        BarChartView chart = v.findViewById(R.id.bar_chart);
        if (chart != null) {
            chart.setData(
                new int[]{s.criticalCount, s.highCount, s.mediumCount, s.lowCount},
                new String[]{"Critical", "High", "Medium", "Low"},
                new int[]{Color.parseColor("#B71C1C"), Color.parseColor("#E65100"),
                          Color.parseColor("#F57F17"), Color.parseColor("#2E7D32")}
            );
        }
    }

    private void set(View v, int id, String text) {
        TextView tv = v.findViewById(id);
        if (tv != null) tv.setText(text);
    }

    // ── Inline custom Canvas bar chart (zero dependencies) ──────────────────
    public static class BarChartView extends View {
        private int[] values;
        private String[] labels;
        private int[] colors;
        private final Paint barPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint valPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);

        public BarChartView(Context ctx) { super(ctx); init(); }
        public BarChartView(Context ctx, AttributeSet a) { super(ctx, a); init(); }
        public BarChartView(Context ctx, AttributeSet a, int d) { super(ctx, a, d); init(); }

        private void init() {
            textPaint.setColor(Color.parseColor("#B0BEC5"));
            textPaint.setTextSize(28f);
            textPaint.setTextAlign(Paint.Align.CENTER);
            valPaint.setColor(Color.WHITE);
            valPaint.setTextSize(32f);
            valPaint.setTextAlign(Paint.Align.CENTER);
            valPaint.setFakeBoldText(true);
        }

        public void setData(int[] values, String[] labels, int[] colors) {
            this.values = values; this.labels = labels; this.colors = colors;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (values == null || values.length == 0) return;
            int w = getWidth(), h = getHeight();
            int n = values.length;
            int max = 1;
            for (int v : values) if (v > max) max = v;

            float barW   = (w - 60f) / n - 20f;
            float maxH   = h - 80f;
            float startX = 30f;

            for (int i = 0; i < n; i++) {
                float barH  = maxH * values[i] / max;
                float left  = startX + i * (barW + 20f);
                float top   = h - 60f - barH;
                float right = left + barW;

                barPaint.setColor(colors[i]);
                canvas.drawRoundRect(new RectF(left, top, right, h - 60f), 8, 8, barPaint);

                // Value above bar
                valPaint.setColor(colors[i]);
                canvas.drawText(String.valueOf(values[i]), left + barW / 2, top - 8, valPaint);

                // Label below bar
                canvas.drawText(labels[i], left + barW / 2, h - 20f, textPaint);
            }
        }
    }
}
