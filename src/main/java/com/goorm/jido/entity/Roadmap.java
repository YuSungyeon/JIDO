package com.goorm.jido.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
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

        long order = 1L;
        for (RoadmapSection s : newSections) {
            RoadmapSection attached = RoadmapSection.builder()
                    .roadmap(this) // ✅ setter 없이 연관관계 주입
                    .title(s.getTitle())
                    .sectionNum(s.getSectionNum() != null ? s.getSectionNum() : order)
                    .build();
            this.roadmapSections.add(attached);
            order++;
        }
    }

    /** 섹션 단건 추가(순서가 필요하면 여기서 부여) */
    public void addSection(RoadmapSection section) {
        // ✅ setter 금지: 전달받은 값을 복사해 연관관계 주입
        RoadmapSection copy = RoadmapSection.builder()
                .roadmap(this)
                .title(section.getTitle())
                .sectionNum(section.getSectionNum())
                .build();
        this.roadmapSections.add(copy);
    }

    /** 섹션 전체 비우기 (필요 시 사용) */
    public void clearSections() {
        this.roadmapSections.clear();
    }

    /** (선택) 섹션 제목 리스트로 통째 교체 */
    public void replaceSectionsByTitles(List<String> titles){
        this.roadmapSections.clear();
        if (titles == null) return;
        long order = 1L;
        for (String t : titles) {
            RoadmapSection s = RoadmapSection.builder()
                    .roadmap(this)
                    .title(t)
                    .sectionNum(order++)
                    .build();
            this.roadmapSections.add(s);
        }
    }

    /** (참고) DTO → 엔티티 매핑함수로 섹션 교체하고 싶을 때 (예시)
     *
     *  public void replaceSectionsFromDtos(List<SectionDto> dtos, Function<SectionDto, RoadmapSection> mapper) {
     *      this.roadmapSections.clear();
     *      if (dtos == null) return;
     *      long order = 1L;
     *      for (SectionDto d : dtos) {
     *          RoadmapSection s = mapper.apply(d);
     *          RoadmapSection attached = RoadmapSection.builder()
     *                  .roadmap(this)
     *                  .title(s.getTitle())
     *                  .sectionNum(s.getSectionNum() != null ? s.getSectionNum() : order++)
     *                  .build();
     *          this.roadmapSections.add(attached);
     *      }
     *  }
     */
}
