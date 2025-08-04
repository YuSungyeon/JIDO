package com.goorm.jido_.service;

import com.goorm.jido_.dto.RoadmapResponseDto;
import com.goorm.jido_.entity.User;
import com.goorm.jido_.dto.RoadmapRequestDto;
import com.goorm.jido_.entity.Roadmap;
import com.goorm.jido_.repository.RoadmapRepository;
import com.goorm.jido_.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoadmapService {
    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;
    private final RoadmapLikeService roadmapLikeService;
    private final RoadmapBookmarkService roadmapBookmarkService;

    public RoadmapResponseDto saveRoadmap(RoadmapRequestDto dto, Long userId) {
        User author = userRepository.findById(dto.authorId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Roadmap roadmap = Roadmap.builder()
                .author(author)
                .title(dto.title())
                .description(dto.description())
                .category(dto.category())
                .isPublic(dto.isPublic())
                .build();

        Roadmap saved = roadmapRepository.save(roadmap);

        return RoadmapResponseDto.from(
                saved,
                0L,         // likeCount
                false,      // likedByMe
                0L,         // bookmarkCount
                false       // bookmarkedByMe
        );
    }

    @Transactional(readOnly = true)
    public Optional<RoadmapResponseDto> getRoadmap(Long id, Long userId) {
        return roadmapRepository.findById(id)
                .map(roadmap -> {
                    long likeCount = roadmapLikeService.countLikes(roadmap.getRoadmapId());
                    boolean likedByMe = roadmapLikeService.isLiked(userId, roadmap.getRoadmapId());

                    long bookmarkCount = roadmapBookmarkService.countBookmarks(roadmap.getRoadmapId());
                    boolean bookmarkedByMe = roadmapBookmarkService.isBookmarked(userId, roadmap.getRoadmapId());

                    return RoadmapResponseDto.from(roadmap, likeCount, likedByMe, bookmarkCount, bookmarkedByMe);
                });
    }

    @Transactional(readOnly = true)
    public List<RoadmapResponseDto> getAllRoadmaps(Long userId) {
        List<Roadmap> roadmaps = roadmapRepository.findAll();

        return roadmaps.stream()
                .map(roadmap -> {
                    long likeCount = roadmapLikeService.countLikes(roadmap.getRoadmapId());
                    boolean likedByMe = roadmapLikeService.isLiked(userId, roadmap.getRoadmapId());

                    long bookmarkCount = roadmapBookmarkService.countBookmarks(roadmap.getRoadmapId());
                    boolean bookmarkedByMe = roadmapBookmarkService.isBookmarked(userId, roadmap.getRoadmapId());

                    return RoadmapResponseDto.from(
                            roadmap, likeCount, likedByMe, bookmarkCount, bookmarkedByMe
                    );
                })
                .toList();
    }

    public void deleteRoadmap(Long id) {
        roadmapRepository.deleteById(id);
    }
}