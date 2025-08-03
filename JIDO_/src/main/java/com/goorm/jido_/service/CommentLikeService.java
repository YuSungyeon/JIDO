package com.goorm.jido_.service;

import com.goorm.jido_.entity.Comment;
import com.goorm.jido_.entity.CommentLike;
import com.goorm.jido_.entity.User;
import com.goorm.jido_.repository.CommentLikeRepository;
import com.goorm.jido_.repository.CommentRepository;
import com.goorm.jido_.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    /**
     * 댓글 좋아요 등록
     *
     * @param userId    사용자 ID
     * @param commentId 좋아요 대상 댓글 ID
     */
    @Transactional
    public void addLike(Long userId, Long commentId) {
        if (likeRepository.existsByUser_UserIdAndComment_CommentId(userId, commentId)) {
            throw new IllegalStateException("이미 좋아요한 댓글입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        CommentLike like = CommentLike.builder()
                .user(user)
                .comment(comment)
                .createdAt(LocalDateTime.now())
                .build();

        likeRepository.save(like);

        // 알림 발송 (본인 댓글 제외)
        if (!userId.equals(comment.getAuthor().getUserId())) {
            notificationService.sendNotification(
                    comment.getAuthor().getUserId(),
                    userId,
                    "comment_like",
                    commentId,
                    user.getNickname() + "님이 회원님의 댓글을 좋아요 했습니다."
            );
        }
    }

    /**
     * 댓글 좋아요 취소
     *
     * @param userId    사용자 ID
     * @param commentId 댓글 ID
     */
    @Transactional
    public void removeLike(Long userId, Long commentId) {
        if (!likeRepository.existsByUser_UserIdAndComment_CommentId(userId, commentId)) {
            throw new IllegalStateException("좋아요하지 않은 댓글입니다.");
        }
        // 알림 삭제 (읽지 않은 경우만)
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        notificationService.deleteUnreadNotification(
                "comment_like", commentId, userId, comment.getAuthor().getUserId()
        );

        likeRepository.deleteByUser_UserIdAndComment_CommentId(userId, commentId);
    }

    /**
     * 댓글 좋아요 수 조회
     *
     * @param commentId 댓글 ID
     * @return 좋아요 개수
     */
    @Transactional(readOnly = true)
    public long countCommentLikes(Long commentId) {
        return likeRepository.countByComment_CommentId(commentId);
    }

    /**
     * 사용자가 해당 댓글을 좋아요 했는지 여부
     */
    @Transactional(readOnly = true)
    public boolean isLiked(Long userId, Long commentId) {
        return likeRepository.existsByUser_UserIdAndComment_CommentId(userId, commentId);
    }

    /**
     * 좋아요가 N개 이상인 댓글 목록 조회
     *
     * @param likeCount 검색 기준 좋아요 값
     * @return 기준값 이상인 댓글 목록
     */
    @Transactional(readOnly = true)
    public List<Comment> getCommentsWithMinLikes(long likeCount) {
        List<Long> commentIds = likeRepository.findCommentIdsWithLikeCountGreaterThanEqual(likeCount);
        return commentRepository.findAllById(commentIds);
    }
}