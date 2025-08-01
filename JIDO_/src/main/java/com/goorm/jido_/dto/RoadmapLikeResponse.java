package com.goorm.jido_.dto;

// 합치면 필요없을듯.
public record RoadmapLikeResponse(
        Long roadmapId,
        long likeCount,
        boolean liked
) {}