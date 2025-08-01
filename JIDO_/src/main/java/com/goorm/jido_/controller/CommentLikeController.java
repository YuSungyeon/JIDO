package com.goorm.jido_.controller;

import com.goorm.jido_.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    /**
     * 댓글 좋아요 등록
     */
    @PostMapping
    public void likeComment(@PathVariable Long commentId,
                            @AuthenticationPrincipal Long userId) {
        commentLikeService.addLike(userId, commentId);
    }

    /**
     * 댓글 좋아요 취소
     */
    @DeleteMapping
    public void unlikeComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal Long userId) {
        commentLikeService.removeLike(userId, commentId);
    }
}