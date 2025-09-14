package com.goorm.jido.controller;

import com.goorm.jido.dto.StepContentUpdateRequestDto;
import com.goorm.jido.entity.StepContent;
import com.goorm.jido.service.StepContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/step-contents")
@RequiredArgsConstructor
public class StepContentController {
    private final StepContentService stepContentService;

    @PostMapping
    public StepContent create(@RequestBody StepContent content) {
        return stepContentService.saveStepContent(content);
    }

    @GetMapping("/{id}")
    public StepContent get(@PathVariable Long id) {
        return stepContentService.getStepContent(id).orElse(null);
    }

    @GetMapping
    public List<StepContent> getAll() {
        return stepContentService.getAllStepContents();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        stepContentService.deleteStepContent(id);
    }

    @PutMapping("/{id}")
    public StepContent update(
            @PathVariable Long id,
            @RequestBody StepContentUpdateRequestDto dto,
            @AuthenticationPrincipal com.goorm.jido.config.CustomUserDetails user
    ) {
        Long userId = (user != null) ? user.getUserId() : null;
        return stepContentService.updateStepContent(id, userId, dto);
    }

}
