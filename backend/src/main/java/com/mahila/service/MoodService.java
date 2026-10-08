package com.mahila.service;

import com.mahila.dto.MoodDTO;
import com.mahila.model.Mood;
import com.mahila.model.User;
import com.mahila.repository.MoodRepository;
import com.mahila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoodService {

    @Autowired
    private MoodRepository moodRepository;

    @Autowired
    private UserRepository userRepository;

    public List<MoodDTO> getMoods(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return moodRepository.findByUserIdOrderByLoggedDateDesc(user.getId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public MoodDTO saveMood(String email, MoodDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Mood mood = Mood.builder()
                .user(user)
                .moodType(dto.getMoodType())
                .intensity(dto.getIntensity() != null ? dto.getIntensity() : 3)
                .loggedDate(dto.getLoggedDate() != null ? dto.getLoggedDate() : LocalDate.now())
                .notes(dto.getNotes())
                .build();

        mood = moodRepository.save(mood);
        return mapToDTO(mood);
    }

    private MoodDTO mapToDTO(Mood m) {
        return MoodDTO.builder()
                .id(m.getId())
                .moodType(m.getMoodType())
                .intensity(m.getIntensity())
                .loggedDate(m.getLoggedDate())
                .notes(m.getNotes())
                .build();
    }
}
