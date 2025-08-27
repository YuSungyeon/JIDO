// StepContentUpdateRequestDto.java
package com.goorm.jido.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StepContentUpdateRequestDto(
        String content,
        Boolean finished
) {}
