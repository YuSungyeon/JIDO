package com.goorm.jido.dto;

import com.goorm.jido.entity.Roadmap;

import java.time.LocalDateTime;

public record RoadmapResponseDto(
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
        LocalDateTime updatedAt
) {
    public static RoadmapResponseDto from(
            Roadmap roadmap,
            long likeCount,
            boolean likedByMe,
            long bookmarkCount,
            boolean bookmarkedByMe
    ) {
        return new RoadmapResponseDto(
                roadmap.getRoadmapId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                roadmap.getCategory(),
                roadmap.getIsPublic(),
                roadmap.getAuthor().getUserId(),
                roadmap.getAuthor().getNickname(),
                likeCount,
                likedByMe,
                bookmarkCount,
                bookmarkedByMe,
                roadmap.getCreatedAt(),
                roadmap.getUpdatedAt()
        );
    }
}