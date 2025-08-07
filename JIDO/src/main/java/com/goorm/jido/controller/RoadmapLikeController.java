package com.goorm.jido.controller;

import com.goorm.jido.service.RoadmapLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roadmaps/{roadmapId}/like")
public class RoadmapLikeController {

    private final RoadmapLikeService roadmapLikeService;

    /**
     * 로드맵 좋아요 등록
     */
    @PostMapping
    public void like(@PathVariable Long roadmapId,
                     @AuthenticationPrincipal Long userId) {
        roadmapLikeService.addLike(userId, roadmapId);
    }

    /**
     * 로드맵 좋아요 취소
     */
    @DeleteMapping
    public void unlike(@PathVariable Long roadmapId,
                       @AuthenticationPrincipal Long userId) {
        roadmapLikeService.removeLike(userId, roadmapId);
    }
}