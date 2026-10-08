package com.mahila.service;

import com.mahila.dto.AiChatRequest;
import com.mahila.dto.AiChatResponse;
import org.springframework.stereotype.Service;

@Service
public class AiAssistantService {

    private static final String DISCLAIMER = "Disclaimer: The AI Assistant provides general educational information only. It does not provide medical diagnoses or replace professional healthcare consultations. Please speak to a doctor for personal medical advice.";

    public AiChatResponse generateResponse(AiChatRequest request) {
        String prompt = request.getPrompt() != null ? request.getPrompt().toLowerCase() : "";
        String responseText;

        if (prompt.contains("pcos") || prompt.contains("ovary")) {
            responseText = "Polycystic Ovary Syndrome (PCOS) is a common hormonal disorder. Key steps include maintaining a balanced low-GI diet, engaging in 30 minutes of regular moderate exercise, managing stress levels, and tracking cycle regularity. Please consult a gynecologist or endocrinologist for personalized evaluations.";
        } else if (prompt.contains("cramp") || prompt.contains("pain")) {
            responseText = "Menstrual cramps can often be eased by using a warm heating pad on your lower abdomen, staying well-hydrated, drinking warm chamomile or ginger tea, and engaging in gentle yoga stretches. If pain is severe or debilitating, consult a medical professional.";
        } else if (prompt.contains("food") || prompt.contains("diet") || prompt.contains("nutrition")) {
            responseText = "During your menstrual cycle, focus on iron-rich foods (spinach, lentils, seeds) along with Vitamin C sources (oranges, bell peppers) to boost absorption. Hydrate frequently and consider magnesium-rich foods like dark chocolate and almonds to reduce muscle cramps.";
        } else if (prompt.contains("ovulation") || prompt.contains("fertile")) {
            responseText = "Ovulation typically occurs around 14 days before your next expected period. Your fertile window spans 5 days leading up to ovulation and ovulation day itself. Signs include clear, stretchy cervical mucus and a slight rise in body temperature.";
        } else {
            responseText = "Thank you for asking! Maintaining a healthy daily routine with balanced nutrition, adequate hydration, 7-9 hours of restful sleep, and cycle tracking helps foster overall reproductive wellness.";
        }

        return AiChatResponse.builder()
                .response(responseText)
                .disclaimer(DISCLAIMER)
                .recommendedCategory("General Wellness")
                .build();
    }
}
