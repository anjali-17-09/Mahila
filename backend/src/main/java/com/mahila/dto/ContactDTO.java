package com.mahila.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {
    private Long id;
    private String contactName;
    private String phoneNumber;
    private String category; // MOTHER, FATHER, FRIEND, DOCTOR, RELATIVE, OTHER
    private Boolean isFavorite;
}
