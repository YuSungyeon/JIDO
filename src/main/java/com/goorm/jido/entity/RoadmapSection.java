package com.goorm.jido.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roadmap_section")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoadmapSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    @JsonIgnore                                  // 역참조 직렬화 방지
    @OnDelete(action = OnDeleteAction.CASCADE)   // 부모 로드맵 삭제 시 DB 레벨에서 섹션도 제거
    private Roadmap roadmap;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "section_num", nullable = false)
    private Long sectionNum;

    @Builder.Default
    @OneToMany(mappedBy = "roadmapSection", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stepNumber ASC")
    private List<Step> steps = new ArrayList<>();

    // ====== 편의 메서드 ======
    // Roadmap.addSection(...) 등 기존 코드 호환용 (package-private로 유지: 외부 API 아님)
    void setRoadmap(Roadmap roadmap) {           // 기존 호출 지점을 살리기 위함 (실제 '세터' 용도 아님)
        this.roadmap = roadmap;
    }

    public void addStep(Step step) {
        if (step == null) return;
        step.assignSection(this);                // 세터 없이 연관 주입
        this.steps.add(step);
    }

    public void removeStep(Step step) {
        if (step == null) return;
        this.steps.remove(step);                 // orphanRemoval=true → DB에서도 삭제
        step.clearSection();
    }

    public void update(String title, Long sectionNum) {
        if (title != null && !title.isBlank()) this.title = title;
        if (sectionNum != null) this.sectionNum = sectionNum;
    }
}
