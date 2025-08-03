package com.goorm.jido_.service;

import com.goorm.jido_.entity.User;
import com.goorm.jido_.dto.RoadmapRequestDto;
import com.goorm.jido_.entity.Roadmap;
import com.goorm.jido_.repository.RoadmapRepository;
import com.goorm.jido_.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoadmapService {
    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;

    public Roadmap saveRoadmap(RoadmapRequestDto dto) {
        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Roadmap roadmap = Roadmap.builder()
                .author(author)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .isPublic(dto.getIsPublic())
                .build();

        return roadmapRepository.save(roadmap);
    }

    public Optional<Roadmap> getRoadmap(Long id) {
        return roadmapRepository.findById(id);
    }

    public List<Roadmap> getAllRoadmaps() {
        return roadmapRepository.findAll();
    }

    public void deleteRoadmap(Long id) {
        roadmapRepository.deleteById(id);
    }
}