package com.goorm.jido_.repository;
import com.goorm.jido_.entity.RoadmapSection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoadmapSectionRepository extends JpaRepository<RoadmapSection, Long> {
    List<RoadmapSection> findByRoadmapIdOrderBySectionNumAsc(Long roadmapId);
}
