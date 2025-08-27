package com.goorm.jido.controller;

import com.goorm.jido.dto.StepUpdateRequestDto;
import com.goorm.jido.entity.Step;
import com.goorm.jido.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/steps")
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;

    @PostMapping
    public Step create(@RequestBody Step step) {
        return stepService.saveStep(step);
    }

    @GetMapping("/{id}")
    public Step get(@PathVariable Long id) {
        return stepService.getStep(id).orElse(null);
    }

    @GetMapping
    public List<Step> getAll() {
        return stepService.getAllSteps();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        stepService.deleteStep(id);
    }

    @PutMapping("/{id}")
    public Step update(
            @PathVariable Long id,
            @RequestBody StepUpdateRequestDto dto,
            @AuthenticationPrincipal com.goorm.jido.config.CustomUserDetails user
    ) {
        Long userId = (user != null) ? user.getUserId() : null;
        return stepService.updateStep(id, userId, dto);
    }

}
