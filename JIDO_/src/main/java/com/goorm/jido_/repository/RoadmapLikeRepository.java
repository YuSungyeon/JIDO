package com.goorm.jido_.repository;

import com.goorm.jido_.entity.RoadmapLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoadmapLikeRepository extends JpaRepository<RoadmapLike, Long> {
    boolean existsByUser_UserIdAndRoadmap_RoadmapId(Long userId, Long roadmapId); // 좋아요 눌렀는지
    long countByRoadmap_RoadmapId(Long roadmapId); // 좋아요 수
    void deleteByUser_UserIdAndRoadmap_RoadmapId(Long userId, Long roadmapId); // 좋아요 취소

    @Query(value = """
        SELECT r.roadmap_id
        FROM roadmap r
        JOIN roadmap_like rl ON r.roadmap_id = rl.roadmap_id
        GROUP BY r.roadmap_id
        HAVING COUNT(rl.like_id) >= :likeCount
    """, nativeQuery = true)
    List<Long> findRoadmapIdsWithLikeCountGreaterThanEqual(@Param("likeCount") long likeCount);
}