package com.mahila.repository;

import com.mahila.model.UserQuizProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserQuizProgressRepository extends JpaRepository<UserQuizProgress, Long> {
    List<UserQuizProgress> findByUserIdOrderByCompletedAtDesc(Long userId);
}
