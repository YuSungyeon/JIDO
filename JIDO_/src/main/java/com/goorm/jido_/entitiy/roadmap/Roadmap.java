package com.goorm.jido_.entitiy.roadmap;

import com.goorm.jido_.entitiy.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 로드맵 엔티티 클래스
 */
@Entity
@Table(name = "roadmap")
@Getter
public class Roadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_id")
    private Long roadmapId; // 로드맵 고유 ID

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user; // 작성자 (User와 연관)

    @Column(name = "title", nullable = false)
    private String title; // 로드맵 제목

    @Column(name = "description", nullable = false)
    private String description; // 설명

    @Column(name = "category", nullable = false)
    private String category; // 카테고리

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true; // 공개 여부 (기본값 true)

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt; // 생성 일시

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt; // 수정 일시

    @OneToMany(mappedBy = "sectionId")
    private List<RoadmapSection> RoadmapSections; // 로드맵에 속한 로드맵 섹션 리스트
}