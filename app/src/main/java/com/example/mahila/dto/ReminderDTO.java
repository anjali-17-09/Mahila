package com.example.mahila.dto;

public class ReminderDTO {
    private Long id;
    private String reminderType;
    private String title;
    private String reminderTime;
    private String repeatDays;
    private Boolean isEnabled;

    public ReminderDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReminderType() { return reminderType; }
    public void setReminderType(String reminderType) { this.reminderType = reminderType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReminderTime() { return reminderTime; }
    public void setReminderTime(String reminderTime) { this.reminderTime = reminderTime; }

    public String getRepeatDays() { return repeatDays; }
    public void setRepeatDays(String repeatDays) { this.repeatDays = repeatDays; }

    public Boolean getIsEnabled() { return isEnabled; }
    public void setIsEnabled(Boolean isEnabled) { this.isEnabled = isEnabled; }
}
