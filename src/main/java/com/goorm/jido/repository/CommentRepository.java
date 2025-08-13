package com.goorm.jido.repository;

import com.goorm.jido.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 로드맵의 댓글 목록 (최신순)
    List<Comment> findByRoadmap_RoadmapIdOrderByCreatedAtDesc(Long roadmapId);
    // 특정 유저가 작성한 댓글 목록 (최신순)
    List<Comment> findByAuthor_UserIdOrderByCreatedAtDesc(Long authorId);
    long countByRoadmap_RoadmapId(Long roadmapId); // 로드맵 댓글 개수
}