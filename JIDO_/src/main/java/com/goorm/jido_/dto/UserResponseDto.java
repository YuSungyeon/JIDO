package com.goorm.jido_.dto;

import com.goorm.jido_.entity.User;

public record UserResponseDto(
        Long userId,
        String userLoginId,
        String email,
        String nickname,
        Integer age
) {
  public static UserResponseDto from(User user) {
    return new UserResponseDto(
            user.getUserId(),
            user.getUserLoginId(),
            user.getEmail(),
            user.getNickname(),
            user.getAge()
    );
  }
}