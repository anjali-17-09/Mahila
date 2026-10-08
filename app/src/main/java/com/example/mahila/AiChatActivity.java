package com.example.mahila;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mahila.api.ApiClient;
import com.example.mahila.api.ApiService;
import com.example.mahila.api.TokenManager;
import org.json.JSONObject;

public class AiChatActivity extends AppCompatActivity {

    private LinearLayout containerChat;
    private EditText etPrompt;
    private ScrollView scrollChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        containerChat = findViewById(R.id.container_chat_messages);
        etPrompt = findViewById(R.id.et_ai_prompt);
        scrollChat = findViewById(R.id.scroll_chat);

        Button btnSend = findViewById(R.id.btn_send_prompt);
        btnSend.setOnClickListener(v -> sendMessage());

        addMessage("Hello! I am your AI Health Assistant. Ask me anything about menstrual health, PCOS, symptoms, nutrition, or self-care.", false);
    }

    private void sendMessage() {
        String query = etPrompt.getText().toString().trim();
        if (query.isEmpty()) return;

        addMessage(query, true);
        etPrompt.setText("");

        try {
            TokenManager tokenManager = new TokenManager(this);
            JSONObject req = new JSONObject();
            req.put("prompt", query);
            req.put("category", "GENERAL");
            req.put("language", tokenManager.getLanguage());

            ApiClient.postRequest(this, ApiService.AI_CHAT, req, new ApiClient.ApiCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject res = new JSONObject(result);
                        String answer = res.optString("response", "Thank you for asking! Maintaining a healthy routine supports reproductive wellness.");
                        addMessage(answer, false);
                    } catch (Exception e) {
                        generateLocalAiResponse(query);
                    }
                }

                @Override
                public void onError(String message) {
                    generateLocalAiResponse(query);
                }
            });
        } catch (Exception e) {
            generateLocalAiResponse(query);
        }
    }

    private void generateLocalAiResponse(String query) {
        String prompt = query.toLowerCase();
        if (prompt.contains("pcos") || prompt.contains("ovary")) {
            addMessage("Polycystic Ovary Syndrome (PCOS) is a common hormonal condition. Staying active with 30 mins of daily exercise, consuming low-GI meals, and managing stress helps regulate symptoms.", false);
        } else if (prompt.contains("cramp") || prompt.contains("pain")) {
            addMessage("To soothe cramps: apply a warm heating pad, drink warm chamomile/ginger tea, and practice gentle stretching.", false);
        } else if (prompt.contains("diet") || prompt.contains("food") || prompt.contains("nutrition")) {
            addMessage("Focus on iron-rich foods (spinach, lentils) and Vitamin C (oranges) during your period to boost absorption.", false);
        } else {
            addMessage("Cycle tracking, proper hydration, and restful sleep are key pillars for women's reproductive health.", false);
        }
    }

    private void addMessage(String text, boolean isUser) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(14f);
        tv.setPadding(24, 16, 24, 16);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);

        if (isUser) {
            params.gravity = android.view.Gravity.END;
            tv.setBackgroundColor(0xFFE1F5FE);
            tv.setTextColor(0xFF0277BD);
        } else {
            params.gravity = android.view.Gravity.START;
            tv.setBackgroundColor(0xFFFCE4EC);
            tv.setTextColor(0xFFC2185B);
        }
        tv.setLayoutParams(params);

        containerChat.addView(tv);
        scrollChat.post(() -> scrollChat.fullScroll(ScrollView.FOCUS_DOWN));
    }
}
