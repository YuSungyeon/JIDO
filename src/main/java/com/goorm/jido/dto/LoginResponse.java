package com.goorm.jido.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
  private Long id; // 유저 테이블의 유저의 고유 ID
  private String message; // 반환 메시지
}
