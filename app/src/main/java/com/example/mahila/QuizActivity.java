package com.example.mahila;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mahila.api.ApiClient;
import com.example.mahila.api.ApiService;
import com.example.mahila.dto.QuizQuestionDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestionText, tvCount;
    private RadioGroup rgOptions;
    private RadioButton rbOpt0, rbOpt1, rbOpt2, rbOpt3;
    private ProgressBar pbQuiz;

    private List<QuizQuestionDTO> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestionText = findViewById(R.id.tv_question_text);
        tvCount = findViewById(R.id.tv_question_count);
        rgOptions = findViewById(R.id.rg_options);
        rbOpt0 = findViewById(R.id.rb_opt0);
        rbOpt1 = findViewById(R.id.rb_opt1);
        rbOpt2 = findViewById(R.id.rb_opt2);
        rbOpt3 = findViewById(R.id.rb_opt3);
        pbQuiz = findViewById(R.id.pb_quiz);

        Button btnNext = findViewById(R.id.btn_next_question);
        btnNext.setOnClickListener(v -> submitAnswer());

        loadQuestions();
    }

    private void loadQuestions() {
        ApiClient.getRequest(this, ApiService.QUIZZES + "?category=Menstrual+Health&difficulty=BEGINNER", new ApiClient.ApiCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray arr = new JSONArray(result);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        QuizQuestionDTO q = new QuizQuestionDTO();
                        q.setQuestionText(obj.optString("questionText"));
                        q.setCorrectOptionIndex(obj.optInt("correctOptionIndex", 0));
                        JSONArray opts = obj.optJSONArray("options");
                        List<String> optList = new ArrayList<>();
                        if (opts != null) {
                            for (int j = 0; j < opts.length(); j++) {
                                optList.add(opts.getString(j));
                            }
                        }
                        q.setOptions(optList);
                        questions.add(q);
                    }
                    displayQuestion();
                } catch (Exception e) {
                    fallbackQuestions();
                }
            }

            @Override
            public void onError(String message) {
                fallbackQuestions();
            }
        });
    }

    private void fallbackQuestions() {
        questions.clear();
        QuizQuestionDTO q1 = new QuizQuestionDTO();
        q1.setQuestionText("What is the average duration of a normal menstrual cycle?");
        q1.setOptions(Arrays.asList("14 days", "28 days (Range: 21-35 days)", "45 days", "60 days"));
        q1.setCorrectOptionIndex(1);

        QuizQuestionDTO q2 = new QuizQuestionDTO();
        q2.setQuestionText("What does PCOS stand for?");
        q2.setOptions(Arrays.asList("Polycystic Ovary Syndrome", "Post Cycle Ovarian Surge", "Primary Cell Ovulation", "Poly Cellular State"));
        q2.setCorrectOptionIndex(0);

        questions.add(q1);
        questions.add(q2);
        displayQuestion();
    }

    private void displayQuestion() {
        if (currentIndex >= questions.size()) {
            Toast.makeText(this, "Quiz Completed! Score: " + score + "/" + questions.size(), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        QuizQuestionDTO q = questions.get(currentIndex);
        tvCount.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        pbQuiz.setMax(questions.size());
        pbQuiz.setProgress(currentIndex + 1);

        tvQuestionText.setText(q.getQuestionText());
        List<String> opts = q.getOptions();
        if (opts.size() > 0) rbOpt0.setText(opts.get(0));
        if (opts.size() > 1) rbOpt1.setText(opts.get(1));
        if (opts.size() > 2) rbOpt2.setText(opts.get(2));
        if (opts.size() > 3) rbOpt3.setText(opts.get(3));

        rgOptions.clearCheck();
    }

    private void submitAnswer() {
        int checkedId = rgOptions.getCheckedRadioButtonId();
        if (checkedId == -1) {
            Toast.makeText(this, "Please select an answer!", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedIdx = -1;
        if (checkedId == R.id.rb_opt0) selectedIdx = 0;
        else if (checkedId == R.id.rb_opt1) selectedIdx = 1;
        else if (checkedId == R.id.rb_opt2) selectedIdx = 2;
        else if (checkedId == R.id.rb_opt3) selectedIdx = 3;

        QuizQuestionDTO q = questions.get(currentIndex);
        if (selectedIdx == q.getCorrectOptionIndex()) {
            score++;
            Toast.makeText(this, "Correct! 🎉", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }

        currentIndex++;
        displayQuestion();
    }
}
