package com.goorm.jido_.repository;

import com.goorm.jido_.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 전체 알림 목록 (최신순 정렬)
    List<Notification> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);

    // 안 읽은 알림 목록 (최신순 정렬)
    List<Notification> findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(Long receiverId);
}