package com.goorm.jido_.service;

import com.goorm.jido_.entity.Roadmap;
import com.goorm.jido_.entity.RoadmapLike;
import com.goorm.jido_.entity.User;
import com.goorm.jido_.repository.RoadmapLikeRepository;
import com.goorm.jido_.repository.RoadmapRepository;
import com.goorm.jido_.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadmapLikeService {
    private final RoadmapLikeRepository likeRepository;
    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    /**
     * 로드맵 좋아요 추가
     *
     * @param userId 사용자 ID
     * @param roadmapId 로드맵 ID
     */
    @Transactional
    public void addLike(Long userId, Long roadmapId) {
        if (likeRepository.existsByUser_UserIdAndRoadmap_RoadmapId(userId, roadmapId)) {
            throw new IllegalStateException("이미 좋아요한 로드맵입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new EntityNotFoundException("로드맵을 찾을 수 없습니다."));

        RoadmapLike like = RoadmapLike.builder()
                .user(user)
                .roadmap(roadmap)
                .createdAt(LocalDateTime.now())
                .build();

        likeRepository.save(like);

        // 알림 발송 (본인 좋아요 시 제외)
        if (!userId.equals(roadmap.getAuthor().getUserId())) {
            notificationService.sendNotification(
                    roadmap.getAuthor().getUserId(), // receiver
                    userId,                         // sender
                    "roadmap_like",                 // type
                    roadmapId,                      // reference ID
                    user.getNickname() + "님이 회원님의 로드맵을 좋아요 했습니다."
            );
        }
    }

    /**
     * 로드맵 좋아요 취소
     *
     * @param userId 사용자 ID
     * @param roadmapId 로드맵 ID
     */
    @Transactional
    public void removeLike(Long userId, Long roadmapId) {
        if (!likeRepository.existsByUser_UserIdAndRoadmap_RoadmapId(userId, roadmapId)) {
            throw new IllegalStateException("좋아요하지 않은 로드맵입니다.");
        }
        // 알림 삭제 (읽지 않은 좋아요 알림만)
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new EntityNotFoundException("로드맵을 찾을 수 없습니다."));

        notificationService.deleteUnreadNotification(
                "roadmap_like", roadmapId, userId, roadmap.getAuthor().getUserId()
        );

        likeRepository.deleteByUser_UserIdAndRoadmap_RoadmapId(userId, roadmapId);
    }

    /**
     * 좋아요 여부 확인
     *
     * @param userId 사용자 ID
     * @param roadmapId 로드맵 ID
     */
    @Transactional(readOnly = true)
    public boolean isLiked(Long userId, Long roadmapId) {
        return likeRepository.existsByUser_UserIdAndRoadmap_RoadmapId(userId, roadmapId);
    }

    /**
     * 로드맵 좋아요 수 조회
     *
     * @param roadmapId 로드맵 ID
     */
    @Transactional(readOnly = true)
    public long countLikes(Long roadmapId) {
        return likeRepository.countByRoadmap_RoadmapId(roadmapId);
    }

    @Transactional(readOnly = true)
    public List<Roadmap> getRoadmapsWithMinLikes(long likeCount) {
        List<Long> roadmapIds = likeRepository.findRoadmapIdsWithLikeCountGreaterThanEqual(likeCount);
        return roadmapRepository.findAllById(roadmapIds);
    }

}
