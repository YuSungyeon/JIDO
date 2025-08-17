package com.goorm.jido.repository;
import com.goorm.jido.entity.StepContent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StepContentRepository extends JpaRepository<StepContent, Long> {
    List<StepContent> findByStep_StepIdOrderByCreatedAtAsc(Long stepId);
}
