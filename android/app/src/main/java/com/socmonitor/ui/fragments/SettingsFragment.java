package com.socmonitor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.socmonitor.databinding.FragmentSettingsBinding;
import com.socmonitor.utils.SessionManager;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SessionManager session = new SessionManager(requireContext());
        binding.tvUserEmail.setText(session.getUserEmail());
        binding.tvUserRole.setText(session.getUserRole());

        binding.switchCriticalNotif.setChecked(true);
        binding.switchHighNotif.setChecked(true);
        binding.switchMediumNotif.setChecked(false);

        binding.btnSaveSettings.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Settings saved", Toast.LENGTH_SHORT).show());

        binding.etBackendUrl.setText("http://your-siem-backend:5000/api");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
