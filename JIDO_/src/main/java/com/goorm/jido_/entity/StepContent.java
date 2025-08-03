package com.goorm.jido_.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "step_content")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepContentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    private Step step;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean isFinished;

    private LocalDateTime finishedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
