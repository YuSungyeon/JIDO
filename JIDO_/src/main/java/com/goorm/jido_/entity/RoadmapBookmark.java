package com.goorm.jido_.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapBookmark {
    @Id
    @GeneratedValue
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
}