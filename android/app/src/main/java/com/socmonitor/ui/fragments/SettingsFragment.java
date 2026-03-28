package com.socmonitor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import com.socmonitor.R;
import com.socmonitor.utils.SessionManager;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SessionManager session = new SessionManager(requireContext());

        TextView tvEmail = view.findViewById(R.id.tv_user_email);
        TextView tvRole  = view.findViewById(R.id.tv_user_role);
        if (tvEmail != null) tvEmail.setText(session.getEmail());
        if (tvRole  != null) tvRole.setText(session.getRole());

        SwitchCompat swCrit   = view.findViewById(R.id.switch_critical_notif);
        SwitchCompat swHigh   = view.findViewById(R.id.switch_high_notif);
        SwitchCompat swMedium = view.findViewById(R.id.switch_medium_notif);
        if (swCrit   != null) swCrit.setChecked(true);
        if (swHigh   != null) swHigh.setChecked(true);
        if (swMedium != null) swMedium.setChecked(false);

        EditText etUrl = view.findViewById(R.id.et_backend_url);
        if (etUrl != null) etUrl.setText("http://your-siem-backend:5000/api");

        Button btnSave = view.findViewById(R.id.btn_save_settings);
        if (btnSave != null)
            btnSave.setOnClickListener(v ->
                    Toast.makeText(requireContext(), "Settings saved", Toast.LENGTH_SHORT).show());
    }
}
