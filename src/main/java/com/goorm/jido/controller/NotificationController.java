package com.goorm.jido.controller;

import com.goorm.jido.config.CustomUserDetails;
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
    public List<NotificationResponse> getNotifications(@AuthenticationPrincipal CustomUserDetails user) {
        return notificationService.getNotificationsForUser(user.getUserId());
    }

    /**
     * 사용자 안 읽은 알림 조회
     */
    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadNotifications(@AuthenticationPrincipal CustomUserDetails user) {
        return notificationService.getUnreadNotifications(user.getUserId());
    }

    /**
     * 알림 읽음 처리 (별도 인증 사용자 정보 불필요)
     */
    @PutMapping("/{notificationId}/read")
    public String readAndRedirect(@PathVariable Long notificationId) {
        return notificationService.markAsReadAndResolveRedirect(notificationId);
    }

    /**
     * 알림 전부 읽음 처리
     */
    @PutMapping("/mark-all-read")
    public void markAllAsRead(@AuthenticationPrincipal CustomUserDetails user) {
        notificationService.markAllAsRead(user.getUserId());
    }

    /**
     * 읽은 알림 전체 삭제
     */
    @DeleteMapping("/read")
    public void deleteReadNotifications(@AuthenticationPrincipal CustomUserDetails user) {
        notificationService.deleteAllReadNotifications(user.getUserId());
    }

}