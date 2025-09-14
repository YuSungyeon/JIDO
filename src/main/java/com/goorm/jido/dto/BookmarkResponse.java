package com.goorm.jido.dto;

import java.time.LocalDateTime;

public record BookmarkResponse(
        Long roadmapId,
        LocalDateTime bookmarkedAt
) {}