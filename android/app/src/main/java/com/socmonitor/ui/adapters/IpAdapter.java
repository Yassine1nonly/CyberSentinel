package com.socmonitor.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socmonitor.R;

import java.util.List;

public class IpAdapter extends RecyclerView.Adapter<IpAdapter.IpViewHolder> {

    private final List<String[]> ipList; // [ip, count]

    public IpAdapter(List<String[]> ipList) {
        this.ipList = ipList;
    }

    @NonNull
    @Override
    public IpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ip, parent, false);
        return new IpViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IpViewHolder holder, int position) {
        String[] item = ipList.get(position);
        holder.tvIp.setText(item[0]);
        holder.tvCount.setText("Events: " + item[1]);
        holder.tvIndex.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() { return ipList.size(); }

    static class IpViewHolder extends RecyclerView.ViewHolder {
        TextView tvIp, tvCount, tvIndex;

        IpViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIp    = itemView.findViewById(R.id.tv_ip);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvIndex = itemView.findViewById(R.id.tv_index);
        }
    }
}
