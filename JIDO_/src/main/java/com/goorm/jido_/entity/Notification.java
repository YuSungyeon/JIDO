package com.goorm.jido_.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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

    private Long referenceId;

    private String message;
    private boolean isRead = false;
    private LocalDateTime createdAt;

}
