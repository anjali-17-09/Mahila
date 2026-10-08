package com.mahila.service;

import com.mahila.dto.UserProfileDTO;
import com.mahila.model.HealthProfile;
import com.mahila.model.User;
import com.mahila.repository.HealthProfileRepository;
import com.mahila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthProfileRepository healthProfileRepository;

    public UserProfileDTO getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HealthProfile profile = healthProfileRepository.findByUserId(user.getId())
                .orElseGet(() -> HealthProfile.builder().user(user).age(25).build());

        return mapToDTO(user, profile);
    }

    @Transactional
    public UserProfileDTO updateProfile(String email, UserProfileDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getName() != null) {
            user.setName(dto.getName());
            userRepository.save(user);
        }

        HealthProfile profile = healthProfileRepository.findByUserId(user.getId())
                .orElseGet(() -> HealthProfile.builder().user(user).build());

        if (dto.getAge() != null) profile.setAge(dto.getAge());
        if (dto.getDateOfBirth() != null) profile.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getWeightKg() != null) profile.setWeightKg(dto.getWeightKg());
        if (dto.getHeightCm() != null) profile.setHeightCm(dto.getHeightCm());
        if (dto.getBloodGroup() != null) profile.setBloodGroup(dto.getBloodGroup());
        if (dto.getCity() != null) profile.setCity(dto.getCity());
        if (dto.getEmergencyMedicalNotes() != null) profile.setEmergencyMedicalNotes(dto.getEmergencyMedicalNotes());
        if (dto.getAverageCycleLength() != null) profile.setAverageCycleLength(dto.getAverageCycleLength());
        if (dto.getAveragePeriodLength() != null) profile.setAveragePeriodLength(dto.getAveragePeriodLength());
        if (dto.getPreferredLanguage() != null) profile.setPreferredLanguage(dto.getPreferredLanguage());

        profile = healthProfileRepository.save(profile);

        return mapToDTO(user, profile);
    }

    private UserProfileDTO mapToDTO(User user, HealthProfile profile) {
        return UserProfileDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(profile.getAge())
                .dateOfBirth(profile.getDateOfBirth())
                .weightKg(profile.getWeightKg())
                .heightCm(profile.getHeightCm())
                .bloodGroup(profile.getBloodGroup())
                .city(profile.getCity())
                .emergencyMedicalNotes(profile.getEmergencyMedicalNotes())
                .averageCycleLength(profile.getAverageCycleLength())
                .averagePeriodLength(profile.getAveragePeriodLength())
                .preferredLanguage(profile.getPreferredLanguage())
                .build();
    }
}
