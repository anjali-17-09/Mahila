package com.mahila.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SymptomDTO {
    private Long id;
    private String symptomName;
    private String severity;
    private LocalDate loggedDate;
}
