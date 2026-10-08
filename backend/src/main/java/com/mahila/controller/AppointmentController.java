package com.mahila.controller;

import com.mahila.dto.AppointmentDTO;
import com.mahila.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAppointments(Authentication authentication) {
        return ResponseEntity.ok(appointmentService.getAppointments(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> saveAppointment(Authentication authentication, @RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(appointmentService.saveAppointment(authentication.getName(), dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(Authentication authentication, @PathVariable Long id, @RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(authentication.getName(), id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(Authentication authentication, @PathVariable Long id) {
        appointmentService.deleteAppointment(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }
}
