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
    // 기본 from (기존과 동일)
    public static RoadmapDetailResponseDto from(Roadmap r, List<SectionDto> sections) {
        return from(r, sections, 0L, false, 0L, false);
    }

    // 오버로드된 from (좋아요/북마크 정보 포함)
    public static RoadmapDetailResponseDto from(
            Roadmap r,
            List<SectionDto> sections,
            long likeCount,
            boolean likedByMe,
            long bookmarkCount,
            boolean bookmarkedByMe
    ) {
        return new RoadmapDetailResponseDto(
                r.getRoadmapId(),
                r.getTitle(),
                r.getDescription(),
                r.getCategory(),
                r.getIsPublic(),
                r.getAuthor().getUserId(),
                r.getAuthor().getNickname(),
                likeCount,
                likedByMe,
                bookmarkCount,
                bookmarkedByMe,
                r.getCreatedAt(),
                r.getUpdatedAt(),
                sections
        );
    }
}