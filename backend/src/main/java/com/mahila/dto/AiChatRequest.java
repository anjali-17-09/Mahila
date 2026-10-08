package com.mahila.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiChatRequest {
    private String prompt;
    private String category; // "SYMPTOM", "NUTRITION", "EXERCISE", "GENERAL"
    private String language; // "en", "kn", "hi", "ta", "te", "ml"
}
