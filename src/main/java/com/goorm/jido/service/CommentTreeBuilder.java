package com.goorm.jido.service;

import com.goorm.jido.dto.CommentResponse;
import com.goorm.jido.entity.Comment;

import java.util.*;

public class CommentTreeBuilder {

    public static List<CommentResponse> buildCommentTree(
            List<Comment> comments,
            Map<Long, Long> likeCountMap,
            Set<Long> likedCommentIdsByMe
    ) {
        // parentId 기준으로 자식 댓글 그룹화
        Map<Long, List<Comment>> childrenMap = new HashMap<>();
        List<Comment> roots = new ArrayList<>();

        for (Comment comment : comments) {
            Long parentId = (comment.getParent() != null) ? comment.getParent().getCommentId() : null;

            if (parentId == null) {
                roots.add(comment); // 최상위 댓글
            } else {
                childrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(comment);
            }
        }

        // 최상위 댓글들부터 재귀적으로 변환
        List<CommentResponse> rootResponses = new ArrayList<>();
        for (Comment root : roots) {
            rootResponses.add(buildResponseRecursive(root, childrenMap, likeCountMap, likedCommentIdsByMe));
        }

        return rootResponses;
    }

    private static CommentResponse buildResponseRecursive(
            Comment comment,
            Map<Long, List<Comment>> childrenMap,
            Map<Long, Long> likeCountMap,
            Set<Long> likedCommentIdsByMe
    ) {
        long likeCount = likeCountMap.getOrDefault(comment.getCommentId(), 0L);
        boolean likedByMe = likedCommentIdsByMe.contains(comment.getCommentId());

        // 자식 댓글 재귀 처리
        List<CommentResponse> replies = new ArrayList<>();
        List<Comment> children = childrenMap.getOrDefault(comment.getCommentId(), List.of());
        for (Comment child : children) {
            replies.add(buildResponseRecursive(child, childrenMap, likeCountMap, likedCommentIdsByMe));
        }

        return CommentResponse.withReplies(comment, likeCount, likedByMe, replies);
    }
}
