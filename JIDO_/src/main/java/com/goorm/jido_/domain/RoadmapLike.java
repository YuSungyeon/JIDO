package com.goorm.jido_.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RoadmapLike {
    @Id
    @GeneratedValue
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
}

