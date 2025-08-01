package com.goorm.jido_.dto;

import com.goorm.jido_.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        String authorNickname,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        long likeCount,
        boolean likedByMe
) {
    public static CommentResponse from(Comment comment, long likeCount, boolean likedByMe) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getAuthor().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                likeCount,
                likedByMe
        );
    }
}