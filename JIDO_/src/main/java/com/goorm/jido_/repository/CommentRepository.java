package com.goorm.jido_.repository;

import com.goorm.jido_.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRoadmapId(Long roadmapId); // 댓글 목록
    List<Comment> findByAuthorId(Long authorId); // 특정 유저 작성 댓글 목록
    long countByRoadmapId(Long roadmapId); // 로드맵 댓글 개수
}