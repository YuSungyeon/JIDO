package com.goorm.jido.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseDto { // 유저 회원가입 결과 반환 dto
  private Long userId;
  private String message;
}
