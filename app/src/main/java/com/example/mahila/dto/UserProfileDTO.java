package com.example.mahila.dto;

public class UserProfileDTO {
    private Long userId;
    private String name;
    private String email;
    private Integer age;
    private String dateOfBirth;
    private Double weightKg;
    private Double heightCm;
    private String bloodGroup;
    private String city;
    private String emergencyMedicalNotes;
    private Integer averageCycleLength;
    private Integer averagePeriodLength;
    private String preferredLanguage;

    public UserProfileDTO() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getEmergencyMedicalNotes() { return emergencyMedicalNotes; }
    public void setEmergencyMedicalNotes(String emergencyMedicalNotes) { this.emergencyMedicalNotes = emergencyMedicalNotes; }

    public Integer getAverageCycleLength() { return averageCycleLength; }
    public void setAverageCycleLength(Integer averageCycleLength) { this.averageCycleLength = averageCycleLength; }

    public Integer getAveragePeriodLength() { return averagePeriodLength; }
    public void setAveragePeriodLength(Integer averagePeriodLength) { this.averagePeriodLength = averagePeriodLength; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }
}
