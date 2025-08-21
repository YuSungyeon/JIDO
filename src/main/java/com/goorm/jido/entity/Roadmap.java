package com.goorm.jido.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", name = "description", nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private String category;

    @Builder.Default
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder.Default
    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoadmapSection> roadmapSections = new ArrayList<>();

    // ==================== 엔티티 헬퍼 ====================

    /** 부분 수정: null 이면 건너뜀 */
    public void updateBasicInfo(String title, String description, String category, Boolean isPublic) {
        if (title != null && !title.isBlank()) this.title = title;
        if (description != null) this.description = description;
        if (category != null && !category.isBlank()) this.category = category;
        if (isPublic != null) this.isPublic = isPublic;
    }

    /** 섹션 전체 교체: 기존 섹션 제거(orphanRemoval) 후 새 섹션으로 대체 */
    public void replaceSections(List<RoadmapSection> newSections) {
        this.roadmapSections.clear(); // orphanRemoval=true 덕분에 DB에서도 삭제됨
        if (newSections == null) return;

        // orderIndex 를 쓰고 있다면 아래 주석 해제해서 순서 지정
        int order = 0;
        for (RoadmapSection s : newSections) {
            s.setRoadmap(this);                 // 연관관계 주인 설정
            // s.setOrderIndex(order++);        // RoadmapSection에 orderIndex가 있다면 사용
            this.roadmapSections.add(s);
        }
    }

    /** 섹션 단건 추가(순서가 필요하면 여기서 부여) */
    public void addSection(RoadmapSection section) {
        section.setRoadmap(this);
        // section.setOrderIndex(this.roadmapSections.size());
        this.roadmapSections.add(section);
    }

    /** 섹션 전체 비우기 (필요 시 사용) */
    public void clearSections() {
        this.roadmapSections.clear();
    }

    /** (선택) DTO → 엔티티 매핑함수로 섹션 교체하고 싶을 때 */
    /*
    public void replaceSectionsFromDtos(List<SectionDto> dtos, Function<SectionDto, RoadmapSection> mapper) {
        this.roadmapSections.clear();
        if (dtos == null) return;
        int order = 0;
        for (SectionDto d : dtos) {
            RoadmapSection s = mapper.apply(d);
            s.setRoadmap(this);
            // s.setOrderIndex(order++);
            this.roadmapSections.add(s);
        }
    }
    */
}
