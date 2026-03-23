package com.socmonitor.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.socmonitor.databinding.FragmentDashboardBinding;
import com.socmonitor.model.DashboardStats;
import com.socmonitor.network.MockDataRepository;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDashboard();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            loadDashboard();
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void loadDashboard() {
        DashboardStats stats = MockDataRepository.getInstance().getDashboardStats();

        binding.tvTotalAlerts.setText(String.valueOf(stats.getTotalAlerts()));
        binding.tvCritical.setText(String.valueOf(stats.getCriticalCount()));
        binding.tvHigh.setText(String.valueOf(stats.getHighCount()));
        binding.tvMedium.setText(String.valueOf(stats.getMediumCount()));
        binding.tvLow.setText(String.valueOf(stats.getLowCount()));
        binding.tvFailedLogins.setText(String.valueOf(stats.getFailedLogins24h()));
        binding.tvMalwareDetections.setText(String.valueOf(stats.getMalwareDetections24h()));
        binding.tvSuspiciousIps.setText(String.valueOf(stats.getSuspiciousIpsCount()));
        binding.tvLastUpdated.setText("Last updated: " + stats.getLastUpdated());

        // Threat level indicator
        String level = stats.getThreatLevel();
        binding.tvThreatLevel.setText("Threat Level: " + level);
        switch (level) {
            case "CRITICAL": binding.tvThreatLevel.setBackgroundColor(0xFFB71C1C); break;
            case "HIGH":     binding.tvThreatLevel.setBackgroundColor(0xFFE65100); break;
            case "MEDIUM":   binding.tvThreatLevel.setBackgroundColor(0xFFF57F17); break;
            default:         binding.tvThreatLevel.setBackgroundColor(0xFF1B5E20); break;
        }

        setupPieChart(stats);
    }

    private void setupPieChart(DashboardStats stats) {
        List<PieEntry> entries = new ArrayList<>();
        if (stats.getCriticalCount() > 0) entries.add(new PieEntry(stats.getCriticalCount(), "Critical"));
        if (stats.getHighCount() > 0)     entries.add(new PieEntry(stats.getHighCount(),     "High"));
        if (stats.getMediumCount() > 0)   entries.add(new PieEntry(stats.getMediumCount(),   "Medium"));
        if (stats.getLowCount() > 0)      entries.add(new PieEntry(stats.getLowCount(),      "Low"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(
                Color.parseColor("#B71C1C"),
                Color.parseColor("#E65100"),
                Color.parseColor("#F57F17"),
                Color.parseColor("#1B5E20")
        );
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(11f);

        PieData data = new PieData(dataSet);
        binding.pieChart.setData(data);
        binding.pieChart.setHoleColor(Color.parseColor("#1A1A2E"));
        binding.pieChart.setHoleRadius(55f);
        binding.pieChart.setCenterText("Alerts\nby Severity");
        binding.pieChart.setCenterTextColor(Color.WHITE);
        binding.pieChart.setCenterTextSize(12f);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.getLegend().setTextColor(Color.WHITE);
        binding.pieChart.animateY(800);
        binding.pieChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
