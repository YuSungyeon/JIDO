package com.goorm.jido.repository;

import com.goorm.jido.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {

    List<Step> findByRoadmapSection_SectionIdOrderByStepNumberAsc(Long sectionId);

    // 🔸 상세 조회용: 여러 섹션 id로 스텝을 한번에 가져오기
    @Query("""
           SELECT s
           FROM Step s
           WHERE s.roadmapSection.sectionId IN :sectionIds
           ORDER BY s.roadmapSection.sectionId ASC, s.stepNumber ASC
           """)
    List<Step> findBySectionIds(@Param("sectionIds") List<Long> sectionIds);
}
