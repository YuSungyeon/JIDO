package com.goorm.jido_.DTO;

import com.goorm.jido_.Entitiy.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
  private final Long userId;
  private final String userLoginId;
  private final String email;
  private final String nickname;
  private final String password;
  private final Integer age;

  public UserResponseDto(User user) {
    this.userId = user.getUserId();
    this.userLoginId = user.getUserLoginId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.password = user.getPassword();
    this.age = user.getAge();
  }
}
