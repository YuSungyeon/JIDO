package com.goorm.jido.dto;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long notificationId,
        String message,
        LocalDateTime createdAt,
        boolean isRead,
        String url
) {}