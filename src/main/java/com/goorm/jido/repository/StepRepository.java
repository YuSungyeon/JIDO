package com.goorm.jido.repository;

import com.goorm.jido.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Long> {

    // 섹션 1개에 속한 스텝 목록(정렬)
    List<Step> findByRoadmapSection_SectionIdOrderByStepNumberAsc(Long sectionId);

    // 상세 조회용: 여러 섹션 id로 스텝을 한번에 가져오기
    @Query("""
           SELECT s
           FROM Step s
           WHERE s.roadmapSection.sectionId IN :sectionIds
           ORDER BY s.roadmapSection.sectionId ASC, s.stepNumber ASC
           """)
    List<Step> findBySectionIds(@Param("sectionIds") List<Long> sectionIds);

    // 수정/삭제 권한 체크용: 작성자(userId) 매칭
    Optional<Step> findByStepIdAndRoadmapSection_Roadmap_Author_UserId(Long stepId, Long userId);

    // 단건 + 콘텐츠까지 fetch-join
    @Query("""
           SELECT s
           FROM Step s
           LEFT JOIN FETCH s.stepContents sc
           WHERE s.stepId = :id
           """)
    Optional<Step> findByIdWithContents(@Param("id") Long id);
}
