package com.mahila.service;

import com.mahila.dto.ReminderDTO;
import com.mahila.model.Reminder;
import com.mahila.model.User;
import com.mahila.repository.ReminderRepository;
import com.mahila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ReminderDTO> getReminders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return reminderRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ReminderDTO saveReminder(String email, ReminderDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reminder reminder = Reminder.builder()
                .user(user)
                .reminderType(dto.getReminderType())
                .title(dto.getTitle())
                .reminderTime(dto.getReminderTime())
                .repeatDays(dto.getRepeatDays() != null ? dto.getRepeatDays() : "DAILY")
                .isEnabled(dto.getIsEnabled() != null ? dto.getIsEnabled() : true)
                .build();

        reminder = reminderRepository.save(reminder);
        return mapToDTO(reminder);
    }

    private ReminderDTO mapToDTO(Reminder r) {
        return ReminderDTO.builder()
                .id(r.getId())
                .reminderType(r.getReminderType())
                .title(r.getTitle())
                .reminderTime(r.getReminderTime())
                .repeatDays(r.getRepeatDays())
                .isEnabled(r.getIsEnabled())
                .build();
    }
}
