package com.goorm.jido.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RoadmapRequestDto(
        @NotBlank(message = "title is required")
        String title,
        @NotBlank(message = "description is required")
        String description,
        @NotBlank(message = "category is required")
        String category,
        @NotNull(message = "sections is required")
        @Valid
        List<SectionDto> sections,
        // 공개여부는 생략 시 true(엔티티 기본값)로 처리 가능
        Boolean isPublic
) {}
