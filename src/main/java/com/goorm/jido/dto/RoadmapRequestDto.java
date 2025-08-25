package com.goorm.jido.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RoadmapRequestDto(
        @NotBlank(message = "title is required")
        String title,

        @NotBlank(message = "description is required")
        String description,

        @NotBlank(message = "category is required")
        String category,

        // 섹션 제목 리스트(비어있지 않게)
        @NotNull(message = "sections is required")
        @Size(min = 1, message = "sections must have at least 1 item")
        @Valid
        List<@NotBlank(message = "section title must not be blank") String> sections,

        // 생략 시 엔티티 기본값(true) 사용
        Boolean isPublic,

        // ✅ 로그인 없을 때만 사용하는 선택 필드 (프론트 테스트용)
        // @Positive(message = "authorId must be positive")
        Long authorId
) {}
