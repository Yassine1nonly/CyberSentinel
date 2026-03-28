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
import com.socmonitor.R;
import com.socmonitor.model.Alert;
import com.socmonitor.network.MockDataRepository;
import com.socmonitor.ui.adapters.IpAdapter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IpsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ips, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Map<String, Integer> ipMap = new LinkedHashMap<>();
        for (Alert a : MockDataRepository.getInstance().getAlerts()) {
            String ip = a.getSourceIp();
            if (ip != null && !ip.equals("-"))
                ipMap.put(ip, ipMap.containsKey(ip) ? ipMap.get(ip) + a.getCount() : a.getCount());
        }

        List<String[]> ipList = new ArrayList<>();
        for (Map.Entry<String, Integer> e : ipMap.entrySet())
            ipList.add(new String[]{e.getKey(), String.valueOf(e.getValue())});

        RecyclerView rv = view.findViewById(R.id.recycler_ips);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(new IpAdapter(ipList));

        TextView tv = view.findViewById(R.id.tv_ip_count);
        if (tv != null) tv.setText(ipList.size() + " suspicious IPs");
    }
}
