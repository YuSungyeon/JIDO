package com.goorm.jido_.repository;

import com.goorm.jido_.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 로드맵의 댓글 목록 (최신순)
    List<Comment> findByRoadmapIdOrderByCreatedAtDesc(Long roadmapId);
    // 특정 유저가 작성한 댓글 목록 (최신순)
    List<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
    long countByRoadmapId(Long roadmapId); // 로드맵 댓글 개수
}