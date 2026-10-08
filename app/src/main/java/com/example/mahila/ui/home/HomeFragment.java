package com.example.mahila.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mahila.AiChatActivity;
import com.example.mahila.R;
import com.example.mahila.api.TokenManager;
import com.example.mahila.utils.CycleCalculator;

public class HomeFragment extends Fragment {

    private TextView tvUserName;
    private TextView tvCycleDay;
    private TextView tvCyclePhase;
    private TextView tvNextPeriod;
    private TextView tvFertileWindow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tvUserName = root.findViewById(R.id.tv_user_name);
        tvCycleDay = root.findViewById(R.id.tv_cycle_day);
        tvCyclePhase = root.findViewById(R.id.tv_cycle_phase);
        tvNextPeriod = root.findViewById(R.id.tv_next_period);
        tvFertileWindow = root.findViewById(R.id.tv_fertile_window);

        TokenManager tokenManager = new TokenManager(requireContext());
        tvUserName.setText(tokenManager.getUserName());

        // Dynamic Cycle Math
        long sampleLastPeriod = System.currentTimeMillis() - (14L * 24 * 60 * 60 * 1000);
        CycleCalculator.CycleInfo info = CycleCalculator.calculateCycleInfo(sampleLastPeriod, 28);

        tvCycleDay.setText("Day " + info.cycleDay + " of Cycle");
        tvCyclePhase.setText(info.currentPhase);
        tvNextPeriod.setText(info.nextPeriodDate);
        tvFertileWindow.setText(info.ovulationWindow);

        Button btnLogPeriod = root.findViewById(R.id.btn_quick_log_period);
        if (btnLogPeriod != null) {
            btnLogPeriod.setOnClickListener(v -> startActivity(new Intent(requireContext(), com.example.mahila.PeriodTrackerActivity.class)));
        }

        Button btnAiChat = root.findViewById(R.id.btn_quick_ai_chat);
        if (btnAiChat != null) {
            btnAiChat.setOnClickListener(v -> startActivity(new Intent(requireContext(), AiChatActivity.class)));
        }

        return root;
    }
}
