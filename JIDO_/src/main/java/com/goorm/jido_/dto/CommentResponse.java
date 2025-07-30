package com.goorm.jido_.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long likeCount;
    private boolean likedByMe;
}