package com.mahila.controller;

import com.mahila.dto.ContactDTO;
import com.mahila.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getContacts(Authentication authentication) {
        return ResponseEntity.ok(contactService.getContacts(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<ContactDTO> saveContact(Authentication authentication, @RequestBody ContactDTO dto) {
        return ResponseEntity.ok(contactService.saveContact(authentication.getName(), dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(Authentication authentication, @PathVariable Long id) {
        contactService.deleteContact(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }
}
