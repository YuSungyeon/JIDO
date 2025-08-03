package com.goorm.jido_.service;

import com.goorm.jido_.entity.Roadmap;
import com.goorm.jido_.entity.RoadmapBookmark;
import com.goorm.jido_.entity.User;
import com.goorm.jido_.repository.RoadmapBookmarkRepository;
import com.goorm.jido_.repository.RoadmapRepository;
import com.goorm.jido_.repository.UserRepository;
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

    /**
     * 사용자가 로드맵을 북마크
     *
     * @param userId    북마크한 사용자 ID
     * @param roadmapId 북마크 대상 로드맵 ID
     */
    @Transactional
    public void addBookmark(Long userId, Long roadmapId) {
        if (bookmarkRepository.existsByUser_UserIdAndRoadmap_RoadmapId(userId, roadmapId)) {
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
                    "roadmap_bookmark",             // type
                    roadmapId,                      // reference ID
                    user.getNickname() + "님이 회원님의 로드맵을 북마크했습니다."
            );
        }
    }

    /**
     * 사용자의 북마크 삭제
     *
     * @param userId    사용자 ID
     * @param roadmapId 북마크 삭제 대상 로드맵 ID
     */
    @Transactional
    public void removeBookmark(Long userId, Long roadmapId) {
        if (!bookmarkRepository.existsByUser_UserIdAndRoadmap_RoadmapId(userId, roadmapId)) {
            throw new IllegalStateException("북마크하지 않은 로드맵입니다.");
        }

        // 알림 삭제 (읽지 않은 북마크 알림만)
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new EntityNotFoundException("로드맵을 찾을 수 없습니다."));

        Long receiverId = roadmap.getAuthor().getUserId();
        if (!userId.equals(receiverId)) {
            notificationService.deleteUnreadNotification(
                    "roadmap_bookmark", roadmapId, userId, receiverId
            );
        }

        bookmarkRepository.deleteByUser_UserIdAndRoadmap_RoadmapId(userId, roadmapId);
    }

    /**
     * 해당 사용자의 북마크 여부 확인
     *
     * @param userId    사용자 ID
     * @param roadmapId 로드맵 ID
     * @return 북마크 여부 (true/false)
     */
    @Transactional(readOnly = true)
    public boolean isBookmarked(Long userId, Long roadmapId) {
        return bookmarkRepository.existsByUser_UserIdAndRoadmap_RoadmapId(userId, roadmapId);
    }

    /**
     * 해당 로드맵이 받은 전체 북마크 수 조회
     *
     * @param roadmapId 로드맵 ID
     */
    @Transactional(readOnly = true)
    public long countBookmarks(Long roadmapId) {
        return bookmarkRepository.countByRoadmap_RoadmapId(roadmapId);
    }

    /**
     * 사용자가 북마크한 로드맵 목록 조회 (최신순)
     *
     * @param userId 사용자 ID
     * @return 해당 사용자가 북마크한 로드맵 리스트
     */
    @Transactional(readOnly = true)
    public List<RoadmapBookmark> getBookmarksByUser(Long userId) {
        return bookmarkRepository.findByUser_UserIdOrderByCreatedAtDesc(userId);
    }
}