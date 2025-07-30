package com.goorm.jido_.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookmarkResponse {
    private Long roadmapId;
    private LocalDateTime bookmarkedAt;
}