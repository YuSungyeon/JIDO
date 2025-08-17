package com.goorm.jido.controller;

import com.goorm.jido.dto.RoadmapRequestDto;
import com.goorm.jido.dto.RoadmapResponseDto;
import com.goorm.jido.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roadmaps")
@RequiredArgsConstructor
public class RoadmapController {
    private final RoadmapService roadmapService;

    // 로드맵 생성
    @PostMapping
    public RoadmapResponseDto create(@RequestBody RoadmapRequestDto dto, @AuthenticationPrincipal Long userId) {
        return roadmapService.saveRoadmap(dto, userId);
    }

    // 특정 로드맵 조회
    @GetMapping("/{id}")
    public RoadmapResponseDto get(@PathVariable Long id, @AuthenticationPrincipal Long userId) {
        return roadmapService.getRoadmap(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("로드맵을 찾을 수 없습니다."));
    }

    // 전체 로드맵 조회
    @GetMapping
    public List<RoadmapResponseDto> getAll(@AuthenticationPrincipal Long userId) {
        return roadmapService.getAllRoadmaps(userId);
    }

    // 로드맵 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roadmapService.deleteRoadmap(id);
    }
}