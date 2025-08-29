package com.goorm.jido.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "step_content")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class StepContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepContentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    @JsonIgnore                         // 🔴 역참조(콘텐츠->스텝) 숨김
    @OnDelete(action = OnDeleteAction.CASCADE)     // ✅ DB 레벨 연쇄삭제 힌트
    private Step step;

    @Column(columnDefinition = "TEXT", name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "finished", nullable = false)
    private Boolean finished = false; // 완료 여부

    public void update(String content, Boolean finished) {
        if (content != null) this.content = content;
        if (finished != null) this.finished = finished;
    }

}