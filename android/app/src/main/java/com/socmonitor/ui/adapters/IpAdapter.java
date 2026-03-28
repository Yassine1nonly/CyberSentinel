package com.socmonitor.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.socmonitor.R;
import java.util.List;

public class IpAdapter extends RecyclerView.Adapter<IpAdapter.VH> {

    private final List<String[]> list;

    public IpAdapter(List<String[]> list) { this.list = list; }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ip, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        h.tvIndex.setText(String.valueOf(pos + 1));
        h.tvIp.setText(list.get(pos)[0]);
        h.tvCount.setText("Events: " + list.get(pos)[1]);
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvIndex, tvIp, tvCount;
        VH(@NonNull View v) {
            super(v);
            tvIndex = v.findViewById(R.id.tv_index);
            tvIp    = v.findViewById(R.id.tv_ip);
            tvCount = v.findViewById(R.id.tv_count);
        }
    }
}
