package com.goorm.jido.controller;

import com.goorm.jido.config.CustomUserDetails;
import com.goorm.jido.service.RoadmapLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roadmaps/{roadmapId}/like")
@Slf4j
public class RoadmapLikeController {

    private final RoadmapLikeService roadmapLikeService;

    /**
     * 로드맵 좋아요 등록
     */
    @PostMapping
    public void like(@PathVariable Long roadmapId,
                     @AuthenticationPrincipal CustomUserDetails user) {
        log.info("좋아요 요청 - userId={}, username={}", user.getUserId(), user.getUsername());
        roadmapLikeService.addLike(user.getUserId(), roadmapId);
    }

    /**
     * 로드맵 좋아요 취소
     */
    @DeleteMapping
    public void unlike(@PathVariable Long roadmapId,
                       @AuthenticationPrincipal CustomUserDetails user) {
        log.info("좋아요 취소 요청 - userId={}, username={}", user.getUserId(), user.getUsername());
        roadmapLikeService.removeLike(user.getUserId(), roadmapId);
    }

    @GetMapping
    public Map<String, Object> getLikeStatus(@PathVariable Long roadmapId,
                                             @AuthenticationPrincipal CustomUserDetails user) {
        boolean liked = false;
        if (user != null) {
            liked = roadmapLikeService.isLiked(user.getUserId(), roadmapId);
        }

        long likeCount = roadmapLikeService.countLikes(roadmapId);

        Map<String, Object> response = new HashMap<>();
        response.put("liked", liked);
        response.put("likeCount", likeCount);
        return response;
    }
}