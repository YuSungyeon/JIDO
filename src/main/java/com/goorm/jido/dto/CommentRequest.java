package com.goorm.jido.dto;

public record CommentRequest(
        String content,
        Long parentId // 대댓글일 경우 부모 댓글 ID, 없으면 null
) {}