package com.goorm.jido.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignupRequestDto { // 유저 회원가입 요청 dto
  private String userLoginId;
  private String password;
  private String email;
  private String nickName;
  private int age;

  List<String> categories; // 관심 카테고리
}
