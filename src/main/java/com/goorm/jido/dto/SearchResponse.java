package com.goorm.jido.dto;

import java.util.List;

public record SearchResponse(
        List<UserSearchResult> users,
        List<RoadmapSearchResult> roadmaps
) {}