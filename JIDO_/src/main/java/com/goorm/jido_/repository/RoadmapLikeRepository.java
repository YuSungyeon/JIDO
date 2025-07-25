package com.goorm.jido_.repository;

import com.goorm.jido_.entity.RoadmapLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapLikeRepository extends JpaRepository<RoadmapLike, Long> {
    boolean existsByUserIdAndRoadmapId(Long userId, Long roadmapId); // 좋아요 눌렀는지
    long countByRoadmapId(Long roadmapId); // 좋아요 수
    void deleteByUserIdAndRoadmapId(Long userId, Long roadmapId); // 좋아요 취소
}