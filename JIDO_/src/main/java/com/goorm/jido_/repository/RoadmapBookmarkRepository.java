package com.goorm.jido_.repository;

import com.goorm.jido_.entity.RoadmapBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadmapBookmarkRepository extends JpaRepository<RoadmapBookmark, Long> {
    List<RoadmapBookmark> findByUser_UserIdOrderByCreatedAtDesc(Long userId); // 유저가 북마크한 로드맵 목록
    boolean existsByUser_UserIdAndRoadmap_RoadmapId(Long userId, Long roadmapId); // 해당 로드맵을 북마크 했지는지 확인
    void deleteByUser_UserIdAndRoadmap_RoadmapId(Long userId, Long roadmapId); // 북마크 취소
    long countByRoadmap_RoadmapId(Long roadmapId); // 로드맵의 북마크 수
}