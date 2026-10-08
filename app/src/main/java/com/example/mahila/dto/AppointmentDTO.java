package com.example.mahila.dto;

public class AppointmentDTO {
    private Long id;
    private String doctorName;
    private String specialization;
    private String clinicOrHospital;
    private String appointmentDateTime;
    private Boolean isCompleted;
    private String notes;

    public AppointmentDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getClinicOrHospital() { return clinicOrHospital; }
    public void setClinicOrHospital(String clinicOrHospital) { this.clinicOrHospital = clinicOrHospital; }

    public String getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(String appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
