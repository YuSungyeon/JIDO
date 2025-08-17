package com.goorm.jido_.controller;

import com.goorm.jido_.dto.RoadmapRequestDto;
import com.goorm.jido_.entity.Roadmap;
import com.goorm.jido_.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/roadmaps")
@RequiredArgsConstructor
public class RoadmapApiAliasController {

    private final RoadmapService roadmapService;

    @PostMapping(consumes = "application/json")
    public Roadmap create(@RequestBody RoadmapRequestDto dto) {
        if (dto.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId가 필요합니다");
        }
        return roadmapService.saveRoadmap(dto, dto.getUserId());
    }
}
