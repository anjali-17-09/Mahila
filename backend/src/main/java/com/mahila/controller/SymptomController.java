package com.mahila.controller;

import com.mahila.dto.SymptomDTO;
import com.mahila.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/symptoms")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    @GetMapping
    public ResponseEntity<List<SymptomDTO>> getSymptoms(Authentication authentication) {
        return ResponseEntity.ok(symptomService.getSymptoms(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<SymptomDTO> saveSymptom(Authentication authentication, @RequestBody SymptomDTO dto) {
        return ResponseEntity.ok(symptomService.saveSymptom(authentication.getName(), dto));
    }
}
