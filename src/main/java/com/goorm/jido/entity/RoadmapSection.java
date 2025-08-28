package com.goorm.jido.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roadmap_section")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) // 프록시 필드 무시
public class RoadmapSection {

    // 섹션 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    @JsonIgnore
    private Roadmap roadmap;

    // 섹션 제목
    @Column(name = "title", nullable = false)
    private String title;

    // 섹션 순서 (int형)
    @Column(name = "section_num", nullable = false)
    private Long sectionNum;

    // 해당 섹션에 포함된 Step 리스트
    @OneToMany(mappedBy = "roadmapSection", cascade = CascadeType.ALL)
    private List<Step> steps;

    public void update(String title, Long sectionNum) {
        if (title != null && !title.isBlank()) this.title = title;
        if (sectionNum != null) this.sectionNum = sectionNum;
    }
}
