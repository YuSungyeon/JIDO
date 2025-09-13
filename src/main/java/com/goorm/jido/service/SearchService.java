package com.goorm.jido.service;

import com.goorm.jido.dto.RoadmapSearchResult;
import com.goorm.jido.dto.SearchResponse;
import com.goorm.jido.dto.UserSearchResult;
import com.goorm.jido.repository.RoadmapRepository;
import com.goorm.jido.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final RoadmapRepository roadmapRepository;

    public SearchResponse search(String query) {
        List<UserSearchResult> users = userRepository.searchByNicknameLikeOrInitial("%" + query + "%");
        List<RoadmapSearchResult> roadmaps = roadmapRepository.searchByTitleOrInitial("%" + query + "%");
        return new SearchResponse(users, roadmaps);
    }
}