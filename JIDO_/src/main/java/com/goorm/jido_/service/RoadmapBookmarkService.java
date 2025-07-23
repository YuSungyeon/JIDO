package com.goorm.jido_.service;

import com.goorm.jido_.domain.Roadmap;
import com.goorm.jido_.domain.RoadmapBookmark;
import com.goorm.jido_.domain.User;
import com.goorm.jido_.repository.RoadmapBookmarkRepository;
import com.goorm.jido_.repository.RoadmapRepository;
import com.goorm.jido_.repository.UserRepository;
import com.goorm.jido_.service.notification.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoadmapBookmarkService {

    private final RoadmapBookmarkRepository bookmarkRepository;
    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public void addBookmark(Long userId, Long roadmapId) {
        if (bookmarkRepository.existsByUserIdAndRoadmapId(userId, roadmapId)) {
            throw new IllegalStateException("이미 북마크한 로드맵입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new EntityNotFoundException("로드맵을 찾을 수 없습니다."));

        RoadmapBookmark bookmark = RoadmapBookmark.builder()
                .user(user)
                .roadmap(roadmap)
                .createdAt(LocalDateTime.now())
                .build();

        bookmarkRepository.save(bookmark);

        // 알림 발송 (본인 북마크 시 제외)
        if (!userId.equals(roadmap.getAuthor().getUserId())) {
            notificationService.sendNotification(
                    roadmap.getAuthor().getUserId(), // receiver
                    userId,                         // sender
                    "bookmark",                     // type
                    roadmapId,                      // reference ID
                    user.getNickname() + "님이 회원님의 로드맵을 북마크했습니다."
            );
        }
    }

    @Transactional
    public void removeBookmark(Long userId, Long roadmapId) {
        if (!bookmarkRepository.existsByUserIdAndRoadmapId(userId, roadmapId)) {
            throw new IllegalStateException("북마크하지 않은 로드맵입니다.");
        }
        bookmarkRepository.deleteByUserIdAndRoadmapId(userId, roadmapId);
    }

    @Transactional(readOnly = true)
    public boolean isBookmarked(Long userId, Long roadmapId) {
        return bookmarkRepository.existsByUserIdAndRoadmapId(userId, roadmapId);
    }

    @Transactional(readOnly = true)
    public long countBookmarks(Long roadmapId) {
        return bookmarkRepository.countByRoadmapId(roadmapId);
    }

    @Transactional(readOnly = true)
    public List<RoadmapBookmark> getBookmarksByUser(Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }
}