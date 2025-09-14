package com.goorm.jido.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPatchRequestDto {
  private String userLoginId;
  private String email;
  private String nickname;
  private String password;
  private Integer age;
}
