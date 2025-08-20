package com.goorm.jido.dto;

import java.util.List;

public record SignupRequestDto(
        String userLoginId,
        String password,
        String email,
        String nickName,
        List<String> categories
) {}