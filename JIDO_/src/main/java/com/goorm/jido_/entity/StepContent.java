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

    @Column(columnDefinition = "TEXT", name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime craeted_at;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updated_at;

    @Column(name = "finished", nullable = false)
    private Boolean finished = false; // 완료 여부
}
