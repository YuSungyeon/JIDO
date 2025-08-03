package com.goorm.jido_.Entitiy.Roadmap;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 스텝 컨텐츠 엔티티 클래스
 */
@Entity
@Table(name = "step_content")
@Getter
public class StepContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_content_id")
    private Long stepContentId; // 컨텐츠 고유 ID

    @ManyToOne
    @JoinColumn(name = "step_id", nullable = false)
    private Step step; // 소속 스텝

    @Column(name = "content", nullable = false)
    private String content; // 내용

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime craeted_at;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updated_at;

    @Column(name = "finished", nullable = false)
    private Boolean finished = false; // 완료 여부
}