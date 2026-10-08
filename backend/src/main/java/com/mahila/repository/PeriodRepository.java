package com.mahila.repository;

import com.mahila.model.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    List<Period> findByUserIdOrderByStartDateDesc(Long userId);
    Optional<Period> findFirstByUserIdOrderByStartDateDesc(Long userId);
}
