package com.goorm.jido.repository;

import com.goorm.jido.entity.StepContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StepContentRepository extends JpaRepository<StepContent, Long> {

    List<StepContent> findByStep_StepIdOrderByCreatedAtAsc(Long stepId);

    // 🔸 상세 조회용: 여러 스텝 id로 콘텐츠를 한번에 가져오기
    @Query("""
           SELECT sc
           FROM StepContent sc
           WHERE sc.step.stepId IN :stepIds
           ORDER BY sc.step.stepId ASC, sc.createdAt ASC
           """)
    List<StepContent> findByStepIds(@Param("stepIds") List<Long> stepIds);
}
