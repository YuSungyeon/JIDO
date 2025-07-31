package com.goorm.jido_.DTO;

import com.goorm.jido_.Entitiy.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  Long userId;
  String userLoginId;
  String email;
  String nickname;
  String password;
  Integer age;

  public static UserResponseDto from(User user) {
    return  new UserResponseDto(
            user.getUserId(),
            user.getUserLoginId(),
            user.getEmail(),
            user.getNickname(),
            user.getPassword(),
            user.getAge()
    );
  }
}
