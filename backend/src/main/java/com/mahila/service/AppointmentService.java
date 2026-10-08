package com.mahila.service;

import com.mahila.dto.AppointmentDTO;
import com.mahila.model.Appointment;
import com.mahila.model.User;
import com.mahila.repository.AppointmentRepository;
import com.mahila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AppointmentDTO> getAppointments(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return appointmentRepository.findByUserIdOrderByAppointmentDateTimeAsc(user.getId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO saveAppointment(String email, AppointmentDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Appointment appointment = Appointment.builder()
                .user(user)
                .doctorName(dto.getDoctorName())
                .specialization(dto.getSpecialization())
                .clinicOrHospital(dto.getClinicOrHospital())
                .appointmentDateTime(dto.getAppointmentDateTime())
                .isCompleted(dto.getIsCompleted() != null ? dto.getIsCompleted() : false)
                .notes(dto.getNotes())
                .build();

        appointment = appointmentRepository.save(appointment);
        return mapToDTO(appointment);
    }

    public AppointmentDTO updateAppointment(String email, Long id, AppointmentDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized update attempt!");
        }

        if (dto.getDoctorName() != null) appointment.setDoctorName(dto.getDoctorName());
        if (dto.getSpecialization() != null) appointment.setSpecialization(dto.getSpecialization());
        if (dto.getClinicOrHospital() != null) appointment.setClinicOrHospital(dto.getClinicOrHospital());
        if (dto.getAppointmentDateTime() != null) appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        if (dto.getIsCompleted() != null) appointment.setIsCompleted(dto.getIsCompleted());
        if (dto.getNotes() != null) appointment.setNotes(dto.getNotes());

        appointment = appointmentRepository.save(appointment);
        return mapToDTO(appointment);
    }

    public void deleteAppointment(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized delete attempt!");
        }

        appointmentRepository.delete(appointment);
    }

    private AppointmentDTO mapToDTO(Appointment a) {
        return AppointmentDTO.builder()
                .id(a.getId())
                .doctorName(a.getDoctorName())
                .specialization(a.getSpecialization())
                .clinicOrHospital(a.getClinicOrHospital())
                .appointmentDateTime(a.getAppointmentDateTime())
                .isCompleted(a.getIsCompleted())
                .notes(a.getNotes())
                .build();
    }
}
