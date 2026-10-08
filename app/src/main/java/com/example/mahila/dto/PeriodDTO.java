package com.example.mahila.dto;

public class PeriodDTO {
    private Long id;
    private String startDate;
    private String endDate;
    private String flowLevel;
    private Integer painScale;
    private String notes;

    public PeriodDTO() {}

    public PeriodDTO(String startDate, String endDate, String flowLevel, Integer painScale, String notes) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.flowLevel = flowLevel;
        this.painScale = painScale;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getFlowLevel() { return flowLevel; }
    public void setFlowLevel(String flowLevel) { this.flowLevel = flowLevel; }

    public Integer getPainScale() { return painScale; }
    public void setPainScale(Integer painScale) { this.painScale = painScale; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
