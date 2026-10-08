package com.example.mahila.dto;

public class SymptomDTO {
    private Long id;
    private String symptomName;
    private String severity;
    private String loggedDate;

    public SymptomDTO() {}

    public SymptomDTO(String symptomName, String severity, String loggedDate) {
        this.symptomName = symptomName;
        this.severity = severity;
        this.loggedDate = loggedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSymptomName() { return symptomName; }
    public void setSymptomName(String symptomName) { this.symptomName = symptomName; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getLoggedDate() { return loggedDate; }
    public void setLoggedDate(String loggedDate) { this.loggedDate = loggedDate; }
}
