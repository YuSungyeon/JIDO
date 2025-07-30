package com.goorm.jido_.repository;

import com.goorm.jido_.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserIdAndCommentId(Long userId, Long commentId); // 좋아요를 눌렀는지
    long countByCommentId(Long commentId); // 댓글별 좋아요 수
    void deleteByUserIdAndCommentId(Long userId, Long commentId); // 좋아요 취소

    @Query(value = """ 
        SELECT c.comment_id
        FROM comment c
        JOIN comment_like cl ON c.comment_id = cl.comment_id
        GROUP BY c.comment_id
        HAVING COUNT(cl.comment_like_id) >= :likeCount
    """, nativeQuery = true)
    List<Long> findCommentIdsWithLikeCountGreaterThanEqual(@Param("likeCount") long likeCount);
}