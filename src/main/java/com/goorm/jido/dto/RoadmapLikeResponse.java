package com.goorm.jido.dto;

// 합치면 필요없을듯.
public record RoadmapLikeResponse(
        Long roadmapId,
        long likeCount,
        boolean liked
) {}