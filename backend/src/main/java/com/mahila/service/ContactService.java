package com.mahila.service;

import com.mahila.dto.ContactDTO;
import com.mahila.model.Contact;
import com.mahila.model.User;
import com.mahila.repository.ContactRepository;
import com.mahila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ContactDTO> getContacts(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return contactRepository.findByUserIdOrderByIsFavoriteDescContactNameAsc(user.getId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO saveContact(String email, ContactDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = Contact.builder()
                .user(user)
                .contactName(dto.getContactName())
                .phoneNumber(dto.getPhoneNumber())
                .category(dto.getCategory() != null ? dto.getCategory() : "OTHER")
                .isFavorite(dto.getIsFavorite() != null ? dto.getIsFavorite() : false)
                .build();

        contact = contactRepository.save(contact);
        return mapToDTO(contact);
    }

    public void deleteContact(String email, Long contactId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (!contact.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized delete attempt!");
        }

        contactRepository.delete(contact);
    }

    private ContactDTO mapToDTO(Contact c) {
        return ContactDTO.builder()
                .id(c.getId())
                .contactName(c.getContactName())
                .phoneNumber(c.getPhoneNumber())
                .category(c.getCategory())
                .isFavorite(c.getIsFavorite())
                .build();
    }
}
