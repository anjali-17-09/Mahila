package com.example.mahila.dto;

import java.util.List;

public class MigrationSyncPayload {
    private String email;
    private String name;
    private Integer age;
    private List<PeriodDTO> periods;
    private List<SymptomDTO> symptoms;
    private List<ContactDTO> contacts;

    public MigrationSyncPayload() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public List<PeriodDTO> getPeriods() { return periods; }
    public void setPeriods(List<PeriodDTO> periods) { this.periods = periods; }

    public List<SymptomDTO> getSymptoms() { return symptoms; }
    public void setSymptoms(List<SymptomDTO> symptoms) { this.symptoms = symptoms; }

    public List<ContactDTO> getContacts() { return contacts; }
    public void setContacts(List<ContactDTO> contacts) { this.contacts = contacts; }
}
