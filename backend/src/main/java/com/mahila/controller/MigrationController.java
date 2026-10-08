package com.mahila.controller;

import com.mahila.dto.MigrationSyncPayload;
import com.mahila.service.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/migration")
public class MigrationController {

    @Autowired
    private MigrationService migrationService;

    @PostMapping("/sync")
    public ResponseEntity<Void> syncData(Authentication authentication, @RequestBody MigrationSyncPayload payload) {
        migrationService.syncLegacyData(authentication.getName(), payload);
        return ResponseEntity.ok().build();
    }
}
