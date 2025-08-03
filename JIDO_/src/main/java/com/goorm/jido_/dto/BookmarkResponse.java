package com.goorm.jido_.dto;

import java.time.LocalDateTime;

public record BookmarkResponse(
        Long roadmapId,
        LocalDateTime bookmarkedAt
) {}