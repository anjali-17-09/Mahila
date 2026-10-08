package com.mahila.service;

import com.mahila.dto.MigrationSyncPayload;
import com.mahila.model.*;
import com.mahila.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MigrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthProfileRepository healthProfileRepository;

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public void syncLegacyData(String authEmail, MigrationSyncPayload payload) {
        User user = userRepository.findByEmail(authEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (payload.getAge() != null) {
            HealthProfile profile = healthProfileRepository.findByUserId(user.getId())
                    .orElseGet(() -> HealthProfile.builder().user(user).build());
            profile.setAge(payload.getAge());
            healthProfileRepository.save(profile);
        }

        if (payload.getPeriods() != null) {
            payload.getPeriods().forEach(dto -> {
                Period period = Period.builder()
                        .user(user)
                        .startDate(dto.getStartDate())
                        .endDate(dto.getEndDate())
                        .flowLevel(dto.getFlowLevel() != null ? dto.getFlowLevel() : "MEDIUM")
                        .painScale(dto.getPainScale() != null ? dto.getPainScale() : 0)
                        .notes(dto.getNotes())
                        .build();
                periodRepository.save(period);
            });
        }

        if (payload.getSymptoms() != null) {
            payload.getSymptoms().forEach(dto -> {
                Symptom symptom = Symptom.builder()
                        .user(user)
                        .symptomName(dto.getSymptomName())
                        .severity(dto.getSeverity() != null ? dto.getSeverity() : "MODERATE")
                        .loggedDate(dto.getLoggedDate())
                        .build();
                symptomRepository.save(symptom);
            });
        }

        if (payload.getContacts() != null) {
            payload.getContacts().forEach(dto -> {
                Contact contact = Contact.builder()
                        .user(user)
                        .contactName(dto.getContactName())
                        .phoneNumber(dto.getPhoneNumber())
                        .category(dto.getCategory() != null ? dto.getCategory() : "OTHER")
                        .isFavorite(dto.getIsFavorite() != null ? dto.getIsFavorite() : false)
                        .build();
                contactRepository.save(contact);
            });
        }
    }
}
