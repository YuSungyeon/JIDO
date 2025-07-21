package com.goorm.jido_.repository;

import com.goorm.jido_.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverId(Long receiverId); // 전체 알림 목록
    List<Notification> findByReceiverIdAndIsReadFalse(Long receiverId); // 안읽은 알람
}