package com.goorm.jido_.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로드맵 생성 요청을 받을 DTO 클래스
 * DB의 Roadmap 테이블 컬럼명 및 타입을 반영하여 작성됨
 */

public record RoadmapRequestDto(
        Long authorId,
        String title,
        String description,
        String category,
        Boolean isPublic
) {}
