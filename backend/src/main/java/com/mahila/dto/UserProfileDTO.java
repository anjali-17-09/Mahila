package com.mahila.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private Long userId;
    private String name;
    private String email;
    private Integer age;
    private LocalDate dateOfBirth;
    private BigDecimal weightKg;
    private BigDecimal heightCm;
    private String bloodGroup;
    private String city;
    private String emergencyMedicalNotes;
    private Integer averageCycleLength;
    private Integer averagePeriodLength;
    private String preferredLanguage;
}
