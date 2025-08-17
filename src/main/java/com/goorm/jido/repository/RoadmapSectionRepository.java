package com.goorm.jido.repository;
import com.goorm.jido.entity.RoadmapSection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoadmapSectionRepository extends JpaRepository<RoadmapSection, Long> {
    List<RoadmapSection> findByRoadmap_RoadmapIdOrderBySectionNumAsc(Long roadmapId);
}
