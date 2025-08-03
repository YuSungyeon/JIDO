package com.goorm.jido_.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    private String type; // "like", "comment", "bookmark"

    private Long referenceId;

    private String message;

    @Column(name = "is_read")
    private boolean read = false;

    private LocalDateTime createdAt;

    public void markAsRead() {
        this.read = true;
    }

}
