package com.mahila.controller;

import com.mahila.dto.AnalyticsDTO;
import com.mahila.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<AnalyticsDTO> getAnalytics(Authentication authentication) {
        return ResponseEntity.ok(analyticsService.getAnalytics(authentication.getName()));
    }
}
