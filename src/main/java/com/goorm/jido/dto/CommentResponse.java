package com.goorm.jido.dto;

import com.goorm.jido.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
        Long commentId,
        Long authorId,
        String authorNickname,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        long likeCount,
        boolean likedByMe,
        List<CommentResponse> replies // 대댓글 리스트
) {
    public static CommentResponse from(Comment comment, long likeCount, boolean likedByMe) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getAuthor().getUserId(),
                comment.getAuthor().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                likeCount,
                likedByMe,
                List.of() // 대댓글은 이후 처리
        );
    }

    public static CommentResponse withReplies(Comment comment, long likeCount, boolean likedByMe, List<CommentResponse> replies) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getAuthor().getUserId(),
                comment.getAuthor().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                likeCount,
                likedByMe,
                replies
        );
    }
}