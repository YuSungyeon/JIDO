package com.goorm.jido_.repository;

import com.goorm.jido_.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserIdAndCommentId(Long userId, Long commentId); // 좋아요를 눌렀는지
    long countByCommentId(Long commentId); // 댓글별 좋아요 수
    void deleteByUserIdAndCommentId(Long userId, Long commentId); // 좋아요 취소
}