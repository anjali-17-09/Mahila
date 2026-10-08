package com.mahila.dto;

import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderDTO {
    private Long id;
    private String reminderType; // PERIOD, OVULATION, MEDICINE, APPOINTMENT, WATER
    private String title;
    private LocalTime reminderTime;
    private String repeatDays;
    private Boolean isEnabled;
}
