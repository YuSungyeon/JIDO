package com.goorm.jido_.domain.controller;

import com.goorm.jido_.domain.entity.Roadmap;
import com.goorm.jido_.domain.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roadmaps")
@RequiredArgsConstructor
public class RoadmapController {
    private final RoadmapService roadmapService;

    @PostMapping
    public Roadmap create(@RequestBody Roadmap roadmap) {
        return roadmapService.saveRoadmap(roadmap);
    }

    @GetMapping("/{id}")
    public Roadmap get(@PathVariable Long id) {
        return roadmapService.getRoadmap(id).orElse(null);
    }

    @GetMapping
    public List<Roadmap> getAll() {
        return roadmapService.getAllRoadmaps();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roadmapService.deleteRoadmap(id);
    }
}
