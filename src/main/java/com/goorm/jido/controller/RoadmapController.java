package com.goorm.jido.controller;

import com.goorm.jido.dto.RoadmapRequestDto;
import com.goorm.jido.dto.RoadmapResponseDto;
import com.goorm.jido.dto.RoadmapUpdateRequestDto;
import com.goorm.jido.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roadmaps")
@Validated
@RequiredArgsConstructor
@Slf4j
public class RoadmapController {

    private final RoadmapService roadmapService;

    // 로드맵 생성
    @PostMapping
    public RoadmapResponseDto create(
            @RequestBody @Valid RoadmapRequestDto dto,
            @AuthenticationPrincipal(expression = "userId") Long userId   // ✅ Principal에서 userId 바로 추출
    ) {
        // 로그인 정보가 없으면 body의 authorId로 대체 허용(프론트 테스트 대비)
        if (userId == null) userId = dto.authorId();
        if (userId == null) throw new IllegalArgumentException("authorId 또는 로그인 정보가 필요합니다.");

        log.info("POST /api/roadmaps userId={} dto={}", userId, dto);
        return roadmapService.saveRoadmap(dto, userId);
    }

    // 특정 로드맵 조회
    @GetMapping("/{id}")
    public RoadmapResponseDto get(
            @PathVariable Long id,
            @AuthenticationPrincipal(expression = "userId") Long userId   // ✅ 동일 처리
    ) {
        return roadmapService.getRoadmap(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("로드맵을 찾을 수 없습니다."));
    }

    // 전체 로드맵 조회
    @GetMapping
    public List<RoadmapResponseDto> getAll(
            @AuthenticationPrincipal(expression = "userId") Long userId   // ✅ 동일 처리
    ) {
        return roadmapService.getAllRoadmaps(userId);
    }

    // 로드맵 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roadmapService.deleteRoadmap(id);
    }
    @PutMapping("/{id}")
    public RoadmapResponseDto update(
            @jakarta.validation.constraints.Positive @PathVariable Long id,
            @jakarta.validation.Valid @RequestBody RoadmapUpdateRequestDto dto,
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required");
        }
        log.info("PUT /api/roadmaps/{} by user={} dto={}", id, userId, dto);
        return roadmapService.updateRoadmap(id, userId, dto);
    }

}