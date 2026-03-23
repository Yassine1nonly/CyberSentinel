package com.socmonitor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.socmonitor.databinding.FragmentIpsBinding;
import com.socmonitor.model.Alert;
import com.socmonitor.network.MockDataRepository;
import com.socmonitor.ui.adapters.IpAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IpsFragment extends Fragment {

    private FragmentIpsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentIpsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Aggregate IPs from alerts
        Map<String, Integer> ipCountMap = new LinkedHashMap<>();
        for (Alert a : MockDataRepository.getInstance().getAlerts()) {
            String ip = a.getSourceIp();
            if (ip != null && !ip.equals("-")) {
                ipCountMap.put(ip, ipCountMap.getOrDefault(ip, 0) + a.getCount());
            }
        }

        List<String[]> ipList = new ArrayList<>();
        for (Map.Entry<String, Integer> e : ipCountMap.entrySet()) {
            ipList.add(new String[]{e.getKey(), String.valueOf(e.getValue())});
        }

        IpAdapter adapter = new IpAdapter(ipList);
        binding.recyclerIps.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerIps.setAdapter(adapter);
        binding.tvIpCount.setText(ipList.size() + " suspicious IPs");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
