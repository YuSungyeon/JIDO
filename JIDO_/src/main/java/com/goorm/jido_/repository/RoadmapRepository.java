package com.goorm.jido_.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goorm.jido_.entity.Roadmap;
import java.util.List;


public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    List<Roadmap> findByAuthorId(Long authorId);
    List<Roadmap> findByCategory(String category);
    List<Roadmap> findByIsPublicTrue();  // 공개 로드맵만 조회
}