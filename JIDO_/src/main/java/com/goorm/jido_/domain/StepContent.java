package com.yourproject.domain.roadmap.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "StepContent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long step_content_id;

    @ManyToOne
    @JoinColumn(name = "step_id", nullable = false)
    private Step step;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean is_finished;

    private LocalDateTime finished_at;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
