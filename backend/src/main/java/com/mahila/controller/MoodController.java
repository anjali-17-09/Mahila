package com.mahila.controller;

import com.mahila.dto.MoodDTO;
import com.mahila.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/moods")
public class MoodController {

    @Autowired
    private MoodService moodService;

    @GetMapping
    public ResponseEntity<List<MoodDTO>> getMoods(Authentication authentication) {
        return ResponseEntity.ok(moodService.getMoods(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<MoodDTO> saveMood(Authentication authentication, @RequestBody MoodDTO dto) {
        return ResponseEntity.ok(moodService.saveMood(authentication.getName(), dto));
    }
}
