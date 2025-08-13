package com.goorm.jido.repository;
import com.goorm.jido.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findByRoadmapSection_SectionIdOrderByStepNumberAsc(Long sectionId);
}
