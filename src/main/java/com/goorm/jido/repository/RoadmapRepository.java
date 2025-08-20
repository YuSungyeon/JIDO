package com.goorm.jido.repository;

import com.goorm.jido.dto.RoadmapSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import com.goorm.jido.entity.Roadmap;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    List<Roadmap> findByAuthor_UserId(Long authorId);
    List<Roadmap> findByCategory(String category);
    List<Roadmap> findByIsPublicTrue();  // 공개 로드맵만 조회
    Optional<Roadmap> findByIdAndAuthorId(Long id, Long authorId);

    @Query("SELECT new com.goorm.jido.dto.RoadmapSearchResult(r.roadmapId, r.title) " +
            "FROM Roadmap r WHERE r.title LIKE %:query%")
    List<RoadmapSearchResult> searchByTitleOrInitial(@Param("query") String query);
}