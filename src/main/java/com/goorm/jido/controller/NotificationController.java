package com.goorm.jido.controller;

import com.goorm.jido.dto.NotificationResponse;
import com.goorm.jido.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 사용자 알림 전체 조회
     */
    @GetMapping
    public List<NotificationResponse> getNotifications(@AuthenticationPrincipal Long userId) {
        return notificationService.getNotificationsForUser(userId);
    }

    /**
     * 사용자 안 읽은 알림 조회
     */
    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadNotifications(@AuthenticationPrincipal Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    /**
     * 알림 읽음 처리
     */
    @PutMapping("/{notificationId}/read")
    public String readAndRedirect(@PathVariable Long notificationId) {
        return notificationService.markAsReadAndResolveRedirect(notificationId);
    }

    /**
     * 알림 전부 읽음 처리
     */
    @PutMapping("/mark-all-read")
    public void markAllAsRead(@AuthenticationPrincipal Long userId) {
        notificationService.markAllAsRead(userId);
    }

    /**
     * 읽은 알림 전체 삭제
     */
    @DeleteMapping("/read")
    public void deleteReadNotifications(@AuthenticationPrincipal Long userId) {
        notificationService.deleteAllReadNotifications(userId);
    }

}