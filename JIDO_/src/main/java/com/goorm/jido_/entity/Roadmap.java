package com.goorm.jido_.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roadmap")
public class Roadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_id")
    private Long roadmapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", name = "description", nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private String category; // 카테고리

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true; // 공개 여부 (기본값 true)

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt; // 생성 일시

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt; // 수정 일시

    @OneToMany(mappedBy = "sectionId", cascade = CascadeType.ALL)
    private List<RoadmapSection> RoadmapSections; // 로드맵에 속한 로드맵 섹션 리스트
}