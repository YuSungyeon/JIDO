package com.goorm.jido_.Entitiy.Roadmap;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 스텝 엔티티 클래스
 */
@Entity
@Table(name = "step")
@Getter
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long stepId; // 스텝 고유 ID

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private RoadmapSection RoadmapSection; // 소속 섹션

    @Column(name = "title", nullable = false)
    private String title; // 스텝 제목

    @Column(name = "step_number", nullable = false)
    private Long stepNumber; // 스텝 순서

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "step")
    private List<StepContent> stepContents; // 해당 스텝의 컨텐츠들
}