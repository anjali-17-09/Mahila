package com.mahila.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiChatResponse {
    private String response;
    private String disclaimer;
    private String recommendedCategory;
}
