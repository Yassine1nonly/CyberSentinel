package com.socmonitor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.socmonitor.databinding.FragmentAlertsBinding;
import com.socmonitor.model.Alert;
import com.socmonitor.network.MockDataRepository;
import com.socmonitor.ui.adapters.AlertsAdapter;

import java.util.List;

public class AlertsFragment extends Fragment {

    private FragmentAlertsBinding binding;
    private AlertsAdapter adapter;
    private String currentFilter = "ALL";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAlertsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AlertsAdapter(requireContext());
        binding.recyclerAlerts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerAlerts.setAdapter(adapter);

        loadAlerts("ALL");

        // Filter chip listeners
        binding.chipAll.setOnClickListener(v -> loadAlerts("ALL"));
        binding.chipCritical.setOnClickListener(v -> loadAlerts("CRITICAL"));
        binding.chipHigh.setOnClickListener(v -> loadAlerts("HIGH"));
        binding.chipMedium.setOnClickListener(v -> loadAlerts("MEDIUM"));
        binding.chipLow.setOnClickListener(v -> loadAlerts("LOW"));

        binding.swipeRefresh.setOnRefreshListener(() -> {
            loadAlerts(currentFilter);
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void loadAlerts(String filter) {
        currentFilter = filter;
        List<Alert> alerts;
        if ("ALL".equals(filter)) {
            alerts = MockDataRepository.getInstance().getAlerts();
        } else {
            alerts = MockDataRepository.getInstance().getAlertsBySeverity(filter);
        }
        adapter.setAlerts(alerts);
        binding.tvCount.setText(alerts.size() + " alerts");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
