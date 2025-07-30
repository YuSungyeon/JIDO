package com.goorm.jido_.dto;

import lombok.*;

@Getter
@AllArgsConstructor
// 합치면 필요없을듯.
public class RoadmapLikeResponse {
    private Long roadmapId;
    private long likeCount;
    private boolean liked;
}
