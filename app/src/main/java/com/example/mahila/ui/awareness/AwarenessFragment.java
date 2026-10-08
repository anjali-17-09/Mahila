package com.example.mahila.ui.awareness;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mahila.QuizActivity;
import com.example.mahila.R;

public class AwarenessFragment extends Fragment {

    private static final String[] CATEGORIES = new String[]{
            "Menstrual Health", "PCOS Awareness", "Nutrition", "Mental Wellness",
            "Reproductive Health", "Hygiene", "Pregnancy Awareness", "Menopause Awareness"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_awareness, container, false);

        Button btnStartQuiz = root.findViewById(R.id.btn_start_quiz);
        btnStartQuiz.setOnClickListener(v -> startActivity(new Intent(requireContext(), QuizActivity.class)));

        LinearLayout containerCategories = root.findViewById(R.id.container_categories);
        for (String cat : CATEGORIES) {
            CardView card = new CardView(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 16);
            card.setLayoutParams(params);
            card.setRadius(24f);
            card.setCardElevation(4f);
            card.setContentPadding(32, 32, 32, 32);

            TextView tv = new TextView(requireContext());
            tv.setText("📖  " + cat);
            tv.setTextSize(16f);
            tv.setTextColor(0xFF333333);
            card.addView(tv);

            containerCategories.addView(card);
        }

        return root;
    }
}
