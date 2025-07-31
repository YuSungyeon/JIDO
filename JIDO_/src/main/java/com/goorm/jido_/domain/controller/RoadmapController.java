package com.goorm.jido_.domain.controller;

<<<<<<< HEAD
import com.goorm.jido_.domain.dto.RoadmapRequestDto;
=======
>>>>>>> origin/feature/roadmap
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

<<<<<<< HEAD
    // 로드맵 생성
    @PostMapping
    public Roadmap create(@RequestBody RoadmapRequestDto dto) {
        return roadmapService.saveRoadmap(dto);
    }

    // 특정 로드맵 조회
=======
    @PostMapping
    public Roadmap create(@RequestBody Roadmap roadmap) {
        return roadmapService.saveRoadmap(roadmap);
    }

>>>>>>> origin/feature/roadmap
    @GetMapping("/{id}")
    public Roadmap get(@PathVariable Long id) {
        return roadmapService.getRoadmap(id).orElse(null);
    }

<<<<<<< HEAD
    // 전체 로드맵 조회
=======
>>>>>>> origin/feature/roadmap
    @GetMapping
    public List<Roadmap> getAll() {
        return roadmapService.getAllRoadmaps();
    }

<<<<<<< HEAD
    // 로드맵 삭제
=======
>>>>>>> origin/feature/roadmap
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roadmapService.deleteRoadmap(id);
    }
}
