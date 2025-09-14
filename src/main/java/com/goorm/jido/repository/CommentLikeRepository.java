package com.goorm.jido.repository;

import com.goorm.jido.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUser_UserIdAndComment_CommentId(Long userId, Long commentId); // 좋아요를 눌렀는지
    long countByComment_CommentId(Long commentId); // 댓글별 좋아요 수
    void deleteByUser_UserIdAndComment_CommentId(Long userId, Long commentId); // 좋아요 취소

    @Query(value = """ 
        SELECT c.comment_id
        FROM comment c
        JOIN comment_like cl ON c.comment_id = cl.comment_id
        GROUP BY c.comment_id
        HAVING COUNT(cl.comment_like_id) >= :likeCount
    """, nativeQuery = true)
    List<Long> findCommentIdsWithLikeCountGreaterThanEqual(@Param("likeCount") long likeCount);

    @Query("""
    SELECT cl.comment.commentId, COUNT(cl)
    FROM CommentLike cl
    WHERE cl.comment.commentId IN :commentIds
    GROUP BY cl.comment.commentId
""")
    List<Object[]> countLikesByCommentIds(@Param("commentIds") List<Long> commentIds);

// 변환은 service 쪽에서 Map<Long, Long> 으로 처리

    @Query("""
    SELECT cl.comment.commentId
    FROM CommentLike cl
    WHERE cl.user.userId = :userId AND cl.comment.commentId IN :commentIds
""")
    List<Long> findLikedCommentIds(@Param("userId") Long userId, @Param("commentIds") List<Long> commentIds);
}