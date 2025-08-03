package com.goorm.jido_.controller;

import com.goorm.jido_.service.RoadmapBookmarkService;
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
                         @AuthenticationPrincipal Long userId) {
        bookmarkService.addBookmark(userId, roadmapId);
    }

    /**
     * 로드맵 북마크 제거
     */
    @DeleteMapping
    public void unbookmark(@PathVariable Long roadmapId,
                           @AuthenticationPrincipal Long userId) {
        bookmarkService.removeBookmark(userId, roadmapId);
    }
}
