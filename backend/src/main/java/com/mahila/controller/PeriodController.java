package com.mahila.controller;

import com.mahila.dto.PeriodDTO;
import com.mahila.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/periods")
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @GetMapping
    public ResponseEntity<List<PeriodDTO>> getPeriods(Authentication authentication) {
        return ResponseEntity.ok(periodService.getPeriods(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<PeriodDTO> savePeriod(Authentication authentication, @RequestBody PeriodDTO dto) {
        return ResponseEntity.ok(periodService.savePeriod(authentication.getName(), dto));
    }
}
