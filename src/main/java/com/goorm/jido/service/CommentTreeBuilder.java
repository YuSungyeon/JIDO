package com.goorm.jido.service;

import com.goorm.jido.dto.CommentResponse;
import com.goorm.jido.entity.Comment;

import java.util.*;

public class CommentTreeBuilder {

    public static List<CommentResponse> buildCommentTree(
            List<Comment> comments,
            Map<Long, Long> likeCountMap,
            Set<Long> likedCommentIdsByMe // 로그인 사용자 기준
    ) {
        Map<Long, CommentResponse> responseMap = new HashMap<>();
        Map<Long, List<CommentResponse>> childrenMap = new HashMap<>();

        List<CommentResponse> roots = new ArrayList<>();

        // 1. 각 댓글을 DTO로 변환해 Map에 저장
        for (Comment comment : comments) {
            long likeCount = likeCountMap.getOrDefault(comment.getCommentId(), 0L);
            boolean likedByMe = likedCommentIdsByMe.contains(comment.getCommentId());

            CommentResponse response = CommentResponse.from(comment, likeCount, likedByMe);
            responseMap.put(comment.getCommentId(), response);

            // 자식용 리스트 초기화
            childrenMap.put(comment.getCommentId(), new ArrayList<>());
        }

        // 2. 각 댓글의 parentId에 따라 자식 연결
        for (Comment comment : comments) {
            Long parentId = comment.getParent() != null ? comment.getParent().getCommentId() : null;
            CommentResponse response = responseMap.get(comment.getCommentId());

            if (parentId == null) {
                // 최상위 댓글
                roots.add(response);
            } else {
                childrenMap.get(parentId).add(response);
            }
        }

        // 3. replies 리스트를 재귀적으로 연결
        for (Comment comment : comments) {
            Long commentId = comment.getCommentId();
            List<CommentResponse> replies = childrenMap.get(commentId);
            if (!replies.isEmpty()) {
                CommentResponse original = responseMap.get(commentId);
                CommentResponse replaced = CommentResponse.withReplies(
                        comment,
                        likeCountMap.getOrDefault(commentId, 0L),
                        likedCommentIdsByMe.contains(commentId),
                        replies
                );
                responseMap.put(commentId, replaced);
            }
        }

        return roots;
    }
}