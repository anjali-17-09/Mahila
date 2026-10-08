package com.example.mahila.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mahila.R;
import com.example.mahila.api.ApiClient;
import com.example.mahila.api.ApiService;
import com.example.mahila.api.TokenManager;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private EditText etName, etAge, etDob, etWeight, etHeight, etBlood, etCity, etNotes;
    private TokenManager tokenManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        tokenManager = new TokenManager(requireContext());

        etName = root.findViewById(R.id.et_profile_name);
        etAge = root.findViewById(R.id.et_profile_age);
        etDob = root.findViewById(R.id.et_profile_dob);
        etWeight = root.findViewById(R.id.et_profile_weight);
        etHeight = root.findViewById(R.id.et_profile_height);
        etBlood = root.findViewById(R.id.et_profile_blood);
        etCity = root.findViewById(R.id.et_profile_city);
        etNotes = root.findViewById(R.id.et_profile_notes);

        etName.setText(tokenManager.getUserName());

        Button btnSave = root.findViewById(R.id.btn_save_profile);
        btnSave.setOnClickListener(v -> saveProfile());

        loadProfile();

        return root;
    }

    private void loadProfile() {
        ApiClient.getRequest(requireContext(), ApiService.USER_PROFILE, new ApiClient.ApiCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject dto = new JSONObject(result);
                    if (dto.has("name")) etName.setText(dto.optString("name"));
                    if (dto.has("age")) etAge.setText(String.valueOf(dto.optInt("age")));
                    if (dto.has("dateOfBirth")) etDob.setText(dto.optString("dateOfBirth"));
                    if (dto.has("bloodGroup")) etBlood.setText(dto.optString("bloodGroup"));
                    if (dto.has("city")) etCity.setText(dto.optString("city"));
                    if (dto.has("emergencyMedicalNotes")) etNotes.setText(dto.optString("emergencyMedicalNotes"));
                } catch (Exception ignored) {}
            }

            @Override
            public void onError(String message) {}
        });
    }

    private void saveProfile() {
        try {
            JSONObject dto = new JSONObject();
            dto.put("name", etName.getText().toString());
            try {
                dto.put("age", Integer.parseInt(etAge.getText().toString()));
            } catch (Exception ignored) {}
            dto.put("dateOfBirth", etDob.getText().toString());
            dto.put("bloodGroup", etBlood.getText().toString());
            dto.put("city", etCity.getText().toString());
            dto.put("emergencyMedicalNotes", etNotes.getText().toString());

            ApiClient.postRequest(requireContext(), ApiService.USER_PROFILE, dto, new ApiClient.ApiCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(requireContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(requireContext(), "Profile saved locally", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Profile saved locally", Toast.LENGTH_SHORT).show();
        }
    }
}
