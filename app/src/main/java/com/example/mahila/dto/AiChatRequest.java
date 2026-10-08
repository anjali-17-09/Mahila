package com.example.mahila.dto;

public class AiChatRequest {
    private String prompt;
    private String category;
    private String language;

    public AiChatRequest() {}

    public AiChatRequest(String prompt, String category, String language) {
        this.prompt = prompt;
        this.category = category;
        this.language = language;
    }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
