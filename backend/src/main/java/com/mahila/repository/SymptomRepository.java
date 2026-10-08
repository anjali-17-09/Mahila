package com.mahila.repository;

import com.mahila.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByUserIdOrderByLoggedDateDesc(Long userId);
}
