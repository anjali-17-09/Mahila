package com.example.mahila.ui.assistance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mahila.R;

public class AssistanceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assistance, container, false);

        Button btnGyno = root.findViewById(R.id.btn_map_gyno);
        Button btnHospitals = root.findViewById(R.id.btn_map_hospitals);
        Button btnClinics = root.findViewById(R.id.btn_map_clinics);
        Button btnCenters = root.findViewById(R.id.btn_map_women_centers);

        btnGyno.setOnClickListener(v -> searchMaps("Gynecologists near me"));
        btnHospitals.setOnClickListener(v -> searchMaps("Hospitals near me"));
        btnClinics.setOnClickListener(v -> searchMaps("Clinics near me"));
        btnCenters.setOnClickListener(v -> searchMaps("Women Health Centers near me"));

        TextView tvTitle = root.findViewById(R.id.tv_age_recommendation_title);
        TextView tvBody = root.findViewById(R.id.tv_age_recommendation_body);

        int age = 24; // Sample age from profile
        if (age >= 13 && age <= 19) {
            tvTitle.setText("Recommendations for Age 13-19 (Adolescent)");
            tvBody.setText("• Adolescent Gynecologists\n• Menstrual Education Specialists\n• PCOS Awareness Experts");
        } else if (age >= 20 && age <= 35) {
            tvTitle.setText("Recommendations for Age 20-35 (Reproductive)");
            tvBody.setText("• Fertility Specialists\n• Obstetricians & Gynecologists\n• PCOS & Thyroid Experts");
        } else if (age >= 36 && age <= 50) {
            tvTitle.setText("Recommendations for Age 35-50 (Hormonal)");
            tvBody.setText("• Hormonal Health Specialists\n• Fibroid Experts\n• Endometriosis Specialists");
        } else {
            tvTitle.setText("Recommendations for Age 50+ (Wellness)");
            tvBody.setText("• Menopause Specialists\n• Bone Health (Osteoporosis) Experts\n• Women's Preventive Wellness");
        }

        return root;
    }

    private void searchMaps(String query) {
        Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(query));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(query)));
            startActivity(webIntent);
        }
    }
}
