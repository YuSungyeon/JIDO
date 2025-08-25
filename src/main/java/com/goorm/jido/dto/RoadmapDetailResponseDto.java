package com.goorm.jido.dto;

import com.goorm.jido.entity.Roadmap;

import java.time.LocalDateTime;
import java.util.List;

public record RoadmapDetailResponseDto(
        Long roadmapId,
        String title,
        String description,
        String category,
        Boolean isPublic,
        Long authorId,
        String authorNickname,
        long likeCount,
        boolean likedByMe,
        long bookmarkCount,
        boolean bookmarkedByMe,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<SectionDto> sections
) {
    public static RoadmapDetailResponseDto from(Roadmap r, List<SectionDto> sections) {
        return new RoadmapDetailResponseDto(
                r.getRoadmapId(),
                r.getTitle(),
                r.getDescription(),
                r.getCategory(),
                r.getIsPublic(),
                r.getAuthor().getUserId(),
                r.getAuthor().getNickname(),
                0L, false, 0L, false,
                r.getCreatedAt(),
                r.getUpdatedAt(),
                sections
        );
    }
}
