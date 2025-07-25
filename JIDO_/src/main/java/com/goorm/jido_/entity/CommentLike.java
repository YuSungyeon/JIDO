package com.goorm.jido_.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CommentLike {
    @Id @GeneratedValue
    private Long commentLikeId;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
}