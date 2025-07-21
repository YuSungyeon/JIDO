package com.goorm.jido_.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String type; // "like", "comment", "bookmark"

    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    private Roadmap referenceId;

    private String message;
    private boolean isRead = false;
    private LocalDateTime createdAt;
}
