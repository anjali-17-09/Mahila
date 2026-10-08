package com.mahila.service;

import com.mahila.dto.SymptomDTO;
import com.mahila.model.Symptom;
import com.mahila.model.User;
import com.mahila.repository.SymptomRepository;
import com.mahila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SymptomDTO> getSymptoms(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return symptomRepository.findByUserIdOrderByLoggedDateDesc(user.getId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SymptomDTO saveSymptom(String email, SymptomDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Symptom symptom = Symptom.builder()
                .user(user)
                .symptomName(dto.getSymptomName())
                .severity(dto.getSeverity() != null ? dto.getSeverity() : "MODERATE")
                .loggedDate(dto.getLoggedDate() != null ? dto.getLoggedDate() : LocalDate.now())
                .build();

        symptom = symptomRepository.save(symptom);
        return mapToDTO(symptom);
    }

    private SymptomDTO mapToDTO(Symptom s) {
        return SymptomDTO.builder()
                .id(s.getId())
                .symptomName(s.getSymptomName())
                .severity(s.getSeverity())
                .loggedDate(s.getLoggedDate())
                .build();
    }
}
