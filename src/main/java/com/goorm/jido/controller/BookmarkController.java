package com.goorm.jido.controller;

import com.goorm.jido.config.CustomUserDetails;
import com.goorm.jido.service.RoadmapBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roadmaps/{roadmapId}/bookmark")
public class BookmarkController {

    private final RoadmapBookmarkService bookmarkService;

    /**
     * 로드맵 북마크 추가
     */
    @PostMapping
    public void bookmark(@PathVariable Long roadmapId,
                         @AuthenticationPrincipal CustomUserDetails user) {
        bookmarkService.addBookmark(user.getUserId(), roadmapId);
    }

    /**
     * 로드맵 북마크 제거
     */
    @DeleteMapping
    public void unbookmark(@PathVariable Long roadmapId,
                           @AuthenticationPrincipal CustomUserDetails user) {
        bookmarkService.removeBookmark(user.getUserId(), roadmapId);
    }
}