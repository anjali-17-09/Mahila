package com.example.mahila.dto;

import java.util.Map;

public class AnalyticsDTO {
    private Double averageCycleLength;
    private String cycleRegularity;
    private String mostCommonSymptom;
    private String mostCommonMood;
    private Integer totalCyclesTracked;
    private Map<String, Integer> moodFrequencies;
    private Map<String, Integer> symptomFrequencies;

    public AnalyticsDTO() {}

    public Double getAverageCycleLength() { return averageCycleLength; }
    public void setAverageCycleLength(Double averageCycleLength) { this.averageCycleLength = averageCycleLength; }

    public String getCycleRegularity() { return cycleRegularity; }
    public void setCycleRegularity(String cycleRegularity) { this.cycleRegularity = cycleRegularity; }

    public String getMostCommonSymptom() { return mostCommonSymptom; }
    public void setMostCommonSymptom(String mostCommonSymptom) { this.mostCommonSymptom = mostCommonSymptom; }

    public String getMostCommonMood() { return mostCommonMood; }
    public void setMostCommonMood(String mostCommonMood) { this.mostCommonMood = mostCommonMood; }

    public Integer getTotalCyclesTracked() { return totalCyclesTracked; }
    public void setTotalCyclesTracked(Integer totalCyclesTracked) { this.totalCyclesTracked = totalCyclesTracked; }

    public Map<String, Integer> getMoodFrequencies() { return moodFrequencies; }
    public void setMoodFrequencies(Map<String, Integer> moodFrequencies) { this.moodFrequencies = moodFrequencies; }

    public Map<String, Integer> getSymptomFrequencies() { return symptomFrequencies; }
    public void setSymptomFrequencies(Map<String, Integer> symptomFrequencies) { this.symptomFrequencies = symptomFrequencies; }
}
