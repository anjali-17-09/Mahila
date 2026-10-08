package com.mahila.controller;

import com.mahila.dto.QuizQuestionDTO;
import com.mahila.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/awareness")
public class AwarenessController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizQuestionDTO>> getQuizzes(@RequestParam(defaultValue = "Menstrual Health") String category,
                                                           @RequestParam(defaultValue = "BEGINNER") String difficulty) {
        return ResponseEntity.ok(quizService.getQuestions(category, difficulty));
    }

    @PostMapping("/quizzes/submit")
    public ResponseEntity<Void> submitQuiz(Authentication authentication,
                                           @RequestParam String category,
                                           @RequestParam String difficulty,
                                           @RequestParam int score,
                                           @RequestParam int total) {
        quizService.saveQuizScore(authentication.getName(), category, difficulty, score, total);
        return ResponseEntity.ok().build();
    }
}
