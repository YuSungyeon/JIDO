package com.goorm.jido.controller;

import com.goorm.jido.config.CustomUserDetails;
import com.goorm.jido.service.RoadmapLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}