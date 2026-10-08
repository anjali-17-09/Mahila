package com.mahila.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodDTO {
    private Long id;
    private String moodType; // HAPPY, SAD, ANGRY, ANXIOUS, STRESSED, CALM
    private Integer intensity;
    private LocalDate loggedDate;
    private String notes;
}
