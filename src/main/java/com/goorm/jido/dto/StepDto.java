package com.goorm.jido.dto;

import com.goorm.jido.entity.Step;
import java.util.List;

public record StepDto(
        Long stepId,
        String title,
        Long stepNumber,
        List<StepContentDto> contents
) {
    public static StepDto of(Step s, List<StepContentDto> contents) {
        return new StepDto(
                s.getStepId(),
                s.getTitle(),
                s.getStepNumber(),
                contents == null ? List.of() : contents
        );
    }
}
