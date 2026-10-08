package com.mahila.service;

import com.mahila.dto.PeriodDTO;
import com.mahila.model.Period;
import com.mahila.model.User;
import com.mahila.repository.PeriodRepository;
import com.mahila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodService {

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PeriodDTO> getPeriods(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return periodRepository.findByUserIdOrderByStartDateDesc(user.getId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PeriodDTO savePeriod(String email, PeriodDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Period period = Period.builder()
                .user(user)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .flowLevel(dto.getFlowLevel() != null ? dto.getFlowLevel() : "MEDIUM")
                .painScale(dto.getPainScale() != null ? dto.getPainScale() : 0)
                .notes(dto.getNotes())
                .build();

        period = periodRepository.save(period);
        return mapToDTO(period);
    }

    private PeriodDTO mapToDTO(Period p) {
        return PeriodDTO.builder()
                .id(p.getId())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .flowLevel(p.getFlowLevel())
                .painScale(p.getPainScale())
                .notes(p.getNotes())
                .build();
    }
}
