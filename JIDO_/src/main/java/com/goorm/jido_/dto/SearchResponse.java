package com.goorm.jido_.dto;

import java.util.List;

public record SearchResponse(
        List<UserSearchResult> users,
        List<RoadmapSearchResult> roadmaps
) {}