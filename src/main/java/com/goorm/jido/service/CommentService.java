package com.goorm.jido.service;

import com.goorm.jido.dto.CommentResponse;
import com.goorm.jido.entity.Comment;
import com.goorm.jido.entity.Roadmap;
import com.goorm.jido.entity.User;
import com.goorm.jido.repository.CommentRepository;
import com.goorm.jido.repository.RoadmapRepository;
import com.goorm.jido.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeService commentLikeService;
    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    /**
     * 댓글 작성
     *
     * @param userId    댓글 작성자 ID
     * @param roadmapId 댓글이 작성될 로드맵 ID
     * @param content   댓글 내용
     */
    @Transactional
    public CommentResponse addComment(Long userId, Long roadmapId, String content, Long parentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new EntityNotFoundException("로드맵을 찾을 수 없습니다."));

        Comment parent = null;
        if (parentId != null) {
            parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = Comment.builder()
                .author(user)
                .roadmap(roadmap)
                .content(content)
                .createdAt(LocalDateTime.now())
                .updatedAt(null) // 수정 안했으므로 null 유지
                .build();

        commentRepository.save(comment);

        // 알림 발송 (작성자가 로드맵 작성자가 아닌 경우만)
        if (!userId.equals(roadmap.getAuthor().getUserId())) {
            notificationService.sendNotification(
                    roadmap.getAuthor().getUserId(),
                    userId,
                    "comment",
                    comment.getCommentId(),
                    user.getNickname() + "님이 회원님의 로드맵에 댓글을 남겼습니다."
            );
        }

        return CommentResponse.from(comment, 0L, false);
    }

    /**
     * 댓글 수정
     *
     * @param userId     수정 요청한 사용자 ID
     * @param commentId  수정할 댓글 ID
     * @param newContent 새로운 댓글 내용
     */
    @Transactional
    public void updateComment(Long userId, Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        if (!comment.getAuthor().getUserId().equals(userId)) {
            throw new IllegalStateException("댓글 작성자만 수정할 수 있습니다.");
        }

        comment.updateContent(newContent);

        commentRepository.save(comment);
    }

    /**
     * 댓글 삭제
     *
     * @param userId    삭제 요청한 사용자 ID
     * @param commentId 삭제할 댓글 ID
     */
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        if (!comment.getAuthor().getUserId().equals(userId)) {
            throw new IllegalStateException("댓글 작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);

        // 관련된 읽지 않은 알림도 함께 삭제
        notificationService.deleteUnreadNotification(
                "comment", commentId, userId, comment.getRoadmap().getAuthor().getUserId()
        );
    }

    /**
     * 특정 로드맵에 달린 모든 댓글 조회
     *
     * @param roadmapId 조회할 로드맵 ID
     * @return 해당 로드맵에 작성된 댓글 리스트
     */
    public List<CommentResponse> getCommentsByRoadmap(Long roadmapId, Long userId) {
        List<Comment> allComments = commentRepository.findByRoadmap_RoadmapIdOrderByCreatedAtDesc(roadmapId);

        // 1. 댓글 ID → 응답 DTO 매핑
        Map<Long, CommentResponse> dtoMap = new HashMap<>();
        Map<Long, List<CommentResponse>> childMap = new HashMap<>();

        for (Comment comment : allComments) {
            long likeCount = commentLikeService.countCommentLikes(comment.getCommentId());
            boolean likedByMe = commentLikeService.isLiked(userId, comment.getCommentId());

            CommentResponse dto = CommentResponse.from(comment, likeCount, likedByMe);
            dtoMap.put(comment.getCommentId(), dto);

            if (comment.getParent() != null) {
                childMap.computeIfAbsent(comment.getParent().getCommentId(), k -> new ArrayList<>())
                        .add(dto);
            }
        }

        // 2. 트리 구조로 묶기
        List<CommentResponse> roots = new ArrayList<>();
        for (Comment comment : allComments) {
            if (comment.getParent() == null) {
                Long cid = comment.getCommentId();
                List<CommentResponse> replies = childMap.getOrDefault(cid, List.of());

                CommentResponse response = CommentResponse.withReplies(
                        comment,
                        commentLikeService.countCommentLikes(cid),
                        commentLikeService.isLiked(userId, cid),
                        replies
                );
                roots.add(response);
            }
        }

        return roots;
    }

    /**
     * 특정 사용자가 작성한 모든 댓글 조회
     *
     * @param userId 사용자 ID
     * @return 해당 사용자가 작성한 댓글 리스트
     */
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByAuthor_UserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * 로드맵에 달린 댓글 수 조회
     *
     * @param roadmapId 대상 로드맵 ID
     * @return 댓글 수
     */
    @Transactional(readOnly = true)
    public long countComments(Long roadmapId) {
        return commentRepository.countByRoadmap_RoadmapId(roadmapId);
    }
}