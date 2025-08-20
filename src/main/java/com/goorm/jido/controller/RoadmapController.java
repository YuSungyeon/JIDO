package com.goorm.jido.controller;

import com.goorm.jido.config.CustomUserDetails;
import com.goorm.jido.dto.RoadmapRequestDto;
import com.goorm.jido.dto.RoadmapResponseDto;
import com.goorm.jido.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roadmaps")
@RequiredArgsConstructor
@Slf4j
public class RoadmapController {

    private final RoadmapService roadmapService;

    // 로드맵 생성
    @PostMapping
    public RoadmapResponseDto create(
            @RequestBody RoadmapRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails  // ✅ CustomUserDetails 전체 주입
    ) {
        Long userId = userDetails != null ? userDetails.getUserId() : dto.authorId();
        if (userId == null) {
            throw new IllegalArgumentException("authorId 또는 로그인 정보가 필요합니다.");
        }

        log.info("POST /roadmaps userId={} dto={}", userId, dto);
        return roadmapService.saveRoadmap(dto, userId);
    }

    // 특정 로드맵 조회
    @GetMapping("/{id}")
    public RoadmapResponseDto get(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getUserId() : null;
        return roadmapService.getRoadmap(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("로드맵을 찾을 수 없습니다."));
    }

    // 전체 로드맵 조회
    @GetMapping
    public List<RoadmapResponseDto> getAll(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getUserId() : null;
        return roadmapService.getAllRoadmaps(userId);
    }

    // 로드맵 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roadmapService.deleteRoadmap(id);
    }
}