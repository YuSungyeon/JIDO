package com.goorm.jido.controller;

import com.goorm.jido.dto.CommentRequest;
import com.goorm.jido.dto.CommentResponse;
import com.goorm.jido.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/roadmaps/{roadmapId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping
    public CommentResponse addComment(@PathVariable Long roadmapId,
                                      @RequestBody CommentRequest request,
                                      @AuthenticationPrincipal Long userId) {
        return commentService.addComment(userId, roadmapId, request.content());
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable Long roadmapId,
                              @PathVariable Long commentId,
                              @RequestBody CommentRequest request,
                              @AuthenticationPrincipal Long userId) {
        commentService.updateComment(userId, commentId, request.content());
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long roadmapId,
                              @PathVariable Long commentId,
                              @AuthenticationPrincipal Long userId) {
        commentService.deleteComment(userId, commentId);
    }

    /**
     * 댓글 목록 조회
     */
    @GetMapping
    public List<CommentResponse> getComments(@PathVariable Long roadmapId,
                                             @AuthenticationPrincipal Long userId) {
        return commentService.getCommentsByRoadmap(roadmapId, userId);
    }
}