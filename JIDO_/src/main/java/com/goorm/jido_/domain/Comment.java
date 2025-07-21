package com.goorm.jido_.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
