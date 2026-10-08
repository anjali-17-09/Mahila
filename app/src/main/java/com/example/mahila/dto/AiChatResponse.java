package com.example.mahila.dto;

public class AiChatResponse {
    private String response;
    private String disclaimer;
    private String recommendedCategory;

    public AiChatResponse() {}

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getDisclaimer() { return disclaimer; }
    public void setDisclaimer(String disclaimer) { this.disclaimer = disclaimer; }

    public String getRecommendedCategory() { return recommendedCategory; }
    public void setRecommendedCategory(String recommendedCategory) { this.recommendedCategory = recommendedCategory; }
}
