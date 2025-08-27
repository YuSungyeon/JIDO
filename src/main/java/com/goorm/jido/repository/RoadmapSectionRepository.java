package com.goorm.jido.repository;

import com.goorm.jido.entity.RoadmapSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoadmapSectionRepository extends JpaRepository<RoadmapSection, Long> {

    // 로드맵의 섹션 목록 조회(정렬)
    List<RoadmapSection> findByRoadmap_RoadmapIdOrderBySectionNumAsc(Long roadmapId);

    // 섹션 단건 + 작성자 권한 체크용 (PUT/DELETE 시 사용)
    Optional<RoadmapSection> findBySectionIdAndRoadmap_Author_UserId(Long sectionId, Long userId);

    //  섹션과 그 안의 스텝까지 한 번에 로드하고 싶을 때
    @Query("""
        select rs
        from RoadmapSection rs
        left join fetch rs.steps s
        where rs.sectionId = :id
    """)
    Optional<RoadmapSection> findByIdWithSteps(@Param("id") Long id);
}
