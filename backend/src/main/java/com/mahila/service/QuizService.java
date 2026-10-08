package com.mahila.service;

import com.mahila.dto.QuizQuestionDTO;
import com.mahila.model.User;
import com.mahila.model.UserQuizProgress;
import com.mahila.repository.UserQuizProgressRepository;
import com.mahila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private UserQuizProgressRepository quizProgressRepository;

    @Autowired
    private UserRepository userRepository;

    public List<QuizQuestionDTO> getQuestions(String category, String difficulty) {
        List<QuizQuestionDTO> list = new ArrayList<>();
        
        list.add(QuizQuestionDTO.builder()
                .id(1L)
                .category("Menstrual Health")
                .difficulty("BEGINNER")
                .questionText("What is the average duration of a normal menstrual cycle?")
                .options(Arrays.asList("14 days", "28 days (Range: 21-35 days)", "45 days", "60 days"))
                .correctOptionIndex(1)
                .explanation("A healthy adult menstrual cycle typically spans 21 to 35 days, with 28 days being average.")
                .build());

        list.add(QuizQuestionDTO.builder()
                .id(2L)
                .category("PCOS Awareness")
                .difficulty("BEGINNER")
                .questionText("What does PCOS stand for?")
                .options(Arrays.asList("Polycystic Ovary Syndrome", "Post Cycle Ovarian Surge", "Primary Cell Ovulation Status", "Poly Cellular Ovarian State"))
                .correctOptionIndex(0)
                .explanation("PCOS stands for Polycystic Ovary Syndrome, a common endocrine disorder.")
                .build());

        list.add(QuizQuestionDTO.builder()
                .id(3L)
                .category("Nutrition")
                .difficulty("BEGINNER")
                .questionText("Which nutrient is essential during menstruation to replace blood loss?")
                .options(Arrays.asList("Iron", "Sodium", "Saturated Fat", "Sugar"))
                .correctOptionIndex(0)
                .explanation("Iron replenishment prevents anemia caused by menstrual blood loss.")
                .build());

        return list;
    }

    public void saveQuizScore(String email, String category, String difficulty, int score, int total) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserQuizProgress progress = UserQuizProgress.builder()
                .user(user)
                .category(category)
                .difficulty(difficulty)
                .score(score)
                .totalQuestions(total)
                .build();

        quizProgressRepository.save(progress);
    }
}
