package com.goorm.jido_.controller;

import com.goorm.jido_.dto.RoadmapRequestDto;
import com.goorm.jido_.entity.Roadmap;
import com.goorm.jido_.entity.User;
import com.goorm.jido_.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roadmaps")
@RequiredArgsConstructor
public class RoadmapController {
    private final RoadmapService roadmapService;

    // ✅ 내가 작성한 로드맵 조회
    @GetMapping("/my")
    public List<Roadmap> getMyRoadmaps(@AuthenticationPrincipal User user) {
        return roadmapService.getMyRoadmaps(user);
    }

    // 로드맵 생성
    @PostMapping
    public Roadmap create(@RequestBody RoadmapRequestDto dto) {
        return roadmapService.saveRoadmap(dto);
    }

    // 특정 로드맵 조회
    @GetMapping("/{id}")
    public Roadmap get(@PathVariable Long id) {
        return roadmapService.getRoadmap(id).orElse(null);
    }

    // 전체 로드맵 조회
    @GetMapping
    public List<Roadmap> getAll() {
        return roadmapService.getAllRoadmaps();
    }

    // 로드맵 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roadmapService.deleteRoadmap(id);
    }
}