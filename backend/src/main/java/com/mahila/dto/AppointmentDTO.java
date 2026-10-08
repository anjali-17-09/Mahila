package com.mahila.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Long id;
    private String doctorName;
    private String specialization;
    private String clinicOrHospital;
    private LocalDateTime appointmentDateTime;
    private Boolean isCompleted;
    private String notes;
}
