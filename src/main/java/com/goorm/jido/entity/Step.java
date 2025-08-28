package com.goorm.jido.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "step")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long stepId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    @JsonIgnore                         // 🔴 역참조(스텝->섹션) 숨김
    private RoadmapSection roadmapSection; // 소속 섹션

    @Column(name = "title", nullable = false)
    private String title; // 스텝 제목

    @Column(name = "step_number", nullable = false)
    private Long stepNumber; // 스텝 순서

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false, nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL)
    private List<StepContent> stepContents; // 해당 스텝의 컨텐츠들

    public void update(String title, Long stepNumber) {
        if (title != null && !title.isBlank()) this.title = title;
        if (stepNumber != null) this.stepNumber = stepNumber;
    }
}