package com.example.mahila.dto;

public class MoodDTO {
    private Long id;
    private String moodType;
    private Integer intensity;
    private String loggedDate;
    private String notes;

    public MoodDTO() {}

    public MoodDTO(String moodType, Integer intensity, String loggedDate, String notes) {
        this.moodType = moodType;
        this.intensity = intensity;
        this.loggedDate = loggedDate;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMoodType() { return moodType; }
    public void setMoodType(String moodType) { this.moodType = moodType; }

    public Integer getIntensity() { return intensity; }
    public void setIntensity(Integer intensity) { this.intensity = intensity; }

    public String getLoggedDate() { return loggedDate; }
    public void setLoggedDate(String loggedDate) { this.loggedDate = loggedDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
