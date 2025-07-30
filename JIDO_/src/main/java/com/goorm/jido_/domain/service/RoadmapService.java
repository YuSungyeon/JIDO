package com.goorm.jido_.domain.service;

import com.goorm.jido_.domain.entity.Roadmap;
import com.goorm.jido_.domain.repository.RoadmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoadmapService {
    private final RoadmapRepository roadmapRepository;

    public Roadmap saveRoadmap(Roadmap roadmap) {
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
