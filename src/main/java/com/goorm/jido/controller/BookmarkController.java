package com.goorm.jido.controller;

import com.goorm.jido.config.CustomUserDetails;
import com.goorm.jido.dto.BookmarkResponse;
import com.goorm.jido.entity.RoadmapBookmark;
import com.goorm.jido.service.RoadmapBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @GetMapping
    public Map<String, Object> getBookmarkStatus(@PathVariable Long roadmapId,
                                                 @AuthenticationPrincipal CustomUserDetails user) {
        boolean bookmarked = false;
        if (user != null) {
            bookmarked = bookmarkService.isBookmarked(user.getUserId(), roadmapId);
        }

        long bookmarkCount = bookmarkService.countBookmarks(roadmapId);

        Map<String, Object> response = new HashMap<>();
        response.put("bookmarked", bookmarked);
        response.put("bookmarkCount", bookmarkCount);

        return response;
    }

    @GetMapping("/my")
    public List<BookmarkResponse> getMyBookmarks(@AuthenticationPrincipal CustomUserDetails user) {
        return bookmarkService.getBookmarksByUser(user.getUserId());
    }
}