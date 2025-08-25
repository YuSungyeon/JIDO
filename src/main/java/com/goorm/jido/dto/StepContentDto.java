package com.goorm.jido.dto;

import com.goorm.jido.entity.StepContent;

public record StepContentDto(
        Long stepContentId,
        String content,
        Boolean finished
) {
    public static StepContentDto from(StepContent c) {
        return new StepContentDto(
                c.getStepContentId(),
                c.getContent(),
                c.getFinished()
        );
    }
}
