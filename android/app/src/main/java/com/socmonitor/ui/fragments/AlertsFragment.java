package com.socmonitor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.chip.Chip;
import com.socmonitor.R;
import com.socmonitor.network.MockDataRepository;
import com.socmonitor.ui.adapters.AlertsAdapter;
import java.util.List;

public class AlertsFragment extends Fragment {

    private AlertsAdapter adapter;
    private String currentFilter = "ALL";
    private TextView tvCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvCount = view.findViewById(R.id.tv_count);
        RecyclerView rv = view.findViewById(R.id.recycler_alerts);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setHasFixedSize(true);

        // RecycledViewPool for low-memory efficiency
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(0, 12);
        rv.setRecycledViewPool(pool);

        adapter = new AlertsAdapter(requireContext());
        rv.setAdapter(adapter);

        loadAlerts("ALL");

        int[] chipIds = {R.id.chip_all, R.id.chip_critical, R.id.chip_high, R.id.chip_medium, R.id.chip_low};
        String[] filters = {"ALL", "CRITICAL", "HIGH", "MEDIUM", "LOW"};
        for (int i = 0; i < chipIds.length; i++) {
            final String f = filters[i];
            Chip chip = view.findViewById(chipIds[i]);
            if (chip != null) chip.setOnClickListener(v -> loadAlerts(f));
        }

        SwipeRefreshLayout srl = view.findViewById(R.id.swipe_refresh);
        srl.setOnRefreshListener(() -> { loadAlerts(currentFilter); srl.setRefreshing(false); });
    }

    private void loadAlerts(String filter) {
        currentFilter = filter;
        List alerts = MockDataRepository.getInstance().filterBySeverity(filter);
        adapter.setAlerts(alerts);
        if (tvCount != null) tvCount.setText(alerts.size() + " alerts");
    }
}
