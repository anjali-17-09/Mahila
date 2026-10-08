package com.mahila.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionDTO {
    private Long id;
    private String category;
    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED
    private String questionText;
    private List<String> options;
    private Integer correctOptionIndex;
    private String explanation;
}
