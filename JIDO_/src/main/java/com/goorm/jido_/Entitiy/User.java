package com.goorm.jido_.Entitiy;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "user")
@Getter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", updatable = false)
  private Long user_id; // 사용자 고유 ID

  @Column(name = "user_login_id", nullable = false, unique = true)
  private String user_login_id; // 로그인용 아이디

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "nickname", nullable = false)
  private String nickname; // 닉네임 (프로필 표시 이름)

  @Column(name = "password_hash", nullable = false, unique = true)
  private String password_hash;

  @Column(name = "age")
  private Integer age;

  @Column(name = "created_at")
  private Date created_at; // 가입 일시

  @Column(name = "updated_at")
  private Date updated_at; // 정보 수정일시
}
