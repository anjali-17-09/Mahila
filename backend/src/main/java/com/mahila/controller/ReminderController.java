package com.mahila.controller;

import com.mahila.dto.ReminderDTO;
import com.mahila.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping
    public ResponseEntity<List<ReminderDTO>> getReminders(Authentication authentication) {
        return ResponseEntity.ok(reminderService.getReminders(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<ReminderDTO> saveReminder(Authentication authentication, @RequestBody ReminderDTO dto) {
        return ResponseEntity.ok(reminderService.saveReminder(authentication.getName(), dto));
    }
}
