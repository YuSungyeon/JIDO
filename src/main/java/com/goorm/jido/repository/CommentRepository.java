package com.goorm.jido.repository;

import com.goorm.jido.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 로드맵의 댓글 목록 (최신순)
    List<Comment> findByRoadmap_RoadmapIdOrderByCreatedAtDesc(Long roadmapId);
    List<Comment> findByRoadmap_RoadmapIdOrderByCreatedAtAsc(Long roadmapId);

    // 특정 유저가 작성한 댓글 목록 (최신순)
    List<Comment> findByAuthor_UserIdOrderByCreatedAtDesc(Long authorId);

    // 로드맵 댓글 개수
    long countByRoadmap_RoadmapId(Long roadmapId);

    // 대댓글이 아닌 댓글만 가져오기
    List<Comment> findByRoadmap_RoadmapIdAndParentIsNullOrderByCreatedAtDesc(Long roadmapId);

    // 특정 댓글에 달린 대댓글 가져오기
    List<Comment> findByParent_CommentIdOrderByCreatedAtAsc(Long parentCommentId);
}
