package com.goorm.jido.controller;

import com.goorm.jido.config.CustomUserDetails;
import com.goorm.jido.dto.NotificationResponse;
import com.goorm.jido.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 사용자 알림 전체 조회
     */
    @GetMapping
    public List<NotificationResponse> getNotifications(@AuthenticationPrincipal CustomUserDetails user) {
        logUserInfo(user);
        return notificationService.getNotificationsForUser(user.getUserId());
    }

    /**
     * 사용자 안 읽은 알림 조회
     */
    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadNotifications(@AuthenticationPrincipal CustomUserDetails user) {
        logUserInfo(user);
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
        logUserInfo(user);
        notificationService.markAllAsRead(user.getUserId());
    }

    /**
     * 읽은 알림 전체 삭제
     */
    @DeleteMapping("/read")
    public void deleteReadNotifications(@AuthenticationPrincipal CustomUserDetails user) {
        logUserInfo(user);
        notificationService.deleteAllReadNotifications(user.getUserId());
    }

    private void logUserInfo(CustomUserDetails user) {
        log.info("알림 로직 - 사용자 확인 - userId={}, username={}, roles={}",
                user.getUserId(),
                user.getUsername(),
                user.getAuthorities());
    }

}