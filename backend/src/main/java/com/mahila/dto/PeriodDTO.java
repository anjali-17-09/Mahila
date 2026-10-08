package com.mahila.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String flowLevel; // LIGHT, MEDIUM, HEAVY, SPOTTING
    private Integer painScale; // 0-10
    private String notes;
}
