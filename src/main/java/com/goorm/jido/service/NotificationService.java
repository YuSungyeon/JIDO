package com.goorm.jido.service;

import com.goorm.jido.entity.Notification;
import com.goorm.jido.entity.User;
import com.goorm.jido.entity.Comment;
import com.goorm.jido.entity.Roadmap;
import com.goorm.jido.dto.NotificationResponse;
import com.goorm.jido.repository.CommentRepository;
import com.goorm.jido.repository.NotificationRepository;
import com.goorm.jido.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 알림 생성
     *
     * @param receiverId    알림을 받는 사용자 ID
     * @param senderId      알림을 보낸 사용자 ID
     * @param type          알림 타입
     * @param referenceId   관련된 ID
     * @param message       알림 메시지 내용
     */
    @Transactional
    public void sendNotification(Long receiverId, Long senderId, String type, Long referenceId, String message) {
        if (receiverId.equals(senderId)) return; // 자기 자신에게는 알림 보내지 않음

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("수신자 정보를 찾을 수 없습니다."));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("발신자 정보를 찾을 수 없습니다."));

        Notification notification = Notification.builder()
                .receiver(receiver)
                .sender(sender)
                .type(type)
                .referenceId(referenceId)
                .message(message)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }

    /**
     * 알림 URL 생성
     *
     * @param type          알림 타입
     * @param referenceId   관련된 ID
     *
     * 댓글 포커싱 관련 (Fragment로 위치 이동 혹은 특정 댓글 포커싱)
     */
    public String resolveNotificationUrl(String type, Long referenceId) {
        return switch (type) {
            case "roadmap_bookmark", "roadmap_like" -> "/roadmaps/" + referenceId;
            case "comment", "comment_like" -> {
                Long roadmapId = commentRepository.findById(referenceId)
                        .map(Comment::getRoadmap)
                        .map(Roadmap::getRoadmapId)
                        .orElseThrow(() -> new EntityNotFoundException("댓글에 해당하는 로드맵을 찾을 수 없습니다."));
                yield "/roadmaps/" + roadmapId + "?highlightComment=" + referenceId;

            }
            default -> "/";
        };
    }

    /**
     * 특정 유저가 받은 모든 알림을 조회 (최신순 정렬)
     *
     * @param userId 유저 ID
     */
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsForUser(Long userId) {
        return notificationRepository.findByReceiver_UserIdOrderByCreatedAtDesc(userId).stream()
                .map(n -> new NotificationResponse(
                        n.getMessage(),
                        n.getCreatedAt(),
                        n.isRead(),
                        resolveNotificationUrl(n.getType(), n.getReferenceId())
                )).toList();
    }

    /**
     * 특정 유저가 안 읽은 모든 알림을 조회 (최신순 정렬)
     * @param userId 유저 ID
     */
    @Transactional(readOnly = true)
    public List<NotificationResponse> getUnreadNotifications(Long userId) {
        return notificationRepository.findByReceiver_UserIdAndReadFalseOrderByCreatedAtDesc(userId).stream()
                .map(n -> new NotificationResponse(
                        n.getMessage(),
                        n.getCreatedAt(),
                        n.isRead(),
                        resolveNotificationUrl(n.getType(), n.getReferenceId())
                )).toList();
    }

    /**
     * 특정 알림을 읽음 처리한 후, 이동할 URL 반환
     *
     * @param notificationId 알림 ID
     * @return 이동할 URL
     */
    @Transactional
    public String markAsReadAndResolveRedirect(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("알림이 존재하지 않습니다."));

        notification.markAsRead(); // 읽음 처리
        notificationRepository.save(notification);

        return resolveNotificationUrl(notification.getType(), notification.getReferenceId());
    }

    /**
     * 사용자가 받은 모든 읽지 않은 알림을 읽음 처리
     *
     * @param userId 유저 ID
     */
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByReceiver_UserIdAndReadFalseOrderByCreatedAtDesc(userId);
        notifications.forEach(Notification::markAsRead);
        notificationRepository.saveAll(notifications);
    }

    /**
     * 특정 유저가 읽은 알림 전체 삭제
     *
     * @param userId 유저 ID
     * */
    @Transactional
    public void deleteAllReadNotifications(Long userId) {
        notificationRepository.deleteByReceiver_UserIdAndReadTrue(userId);
    }

    /**
     * 알림 취소
     *
     * @param type          알림 타입
     * @param referenceId   관련된 ID
     * @param senderId      알림을 보낸 사용자 ID
     * @param receiverId    알림을 받는 사용자 ID
     * */
    @Transactional
    public void deleteUnreadNotification(String type, Long referenceId, Long senderId, Long receiverId) {
        notificationRepository.deleteBySender_UserIdAndReceiver_UserIdAndTypeAndReferenceIdAndReadFalse(
                senderId, receiverId, type, referenceId
        );
    }
}