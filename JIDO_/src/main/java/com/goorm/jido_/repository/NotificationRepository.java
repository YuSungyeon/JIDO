package com.goorm.jido_.repository;

import com.goorm.jido_.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 전체 알림 목록 (최신순 정렬)
    List<Notification> findByReceiver_UserIdOrderByCreatedAtDesc(Long receiverId);

    // 안 읽은 알림 목록 (최신순 정렬)
    List<Notification> findByReceiver_UserIdAndReadFalseOrderByCreatedAtDesc(Long receiverId);

    // 읽은 알림 전체 삭제
    void deleteByReceiver_UserIdAndReadTrue(Long receiverId);

    // 알림 취소 (읽지 않았을 경우만)
    void deleteBySender_UserIdAndReceiver_UserIdAndTypeAndReferenceIdAndReadFalse(
            Long senderId, Long receiverId, String type, Long referenceId
    );
}