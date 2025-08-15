package com.goorm.jido.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId; // 사용자 고유 ID

    @Column(name = "user_login_id", nullable = false, unique = true)
    private String userLoginId; // 로그인용 아이디

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname; // 닉네임 (프로필 표시 이름)

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "age")
    private Integer age;


//    // created_at 열이 "DEFAULT CURRENT_TIMESTAMP" 이므로, 아래와 같이 설정
//    // insertable = false -> 	JPA가 insert 시 이 컬럼 값을 쿼리에 포함하지 않음
//    // updatable = false -> JPA가 update 시 이 컬럼 값을 건드리지 않음
//    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
//    private LocalDateTime createdAt; // 가입 일시

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, nullable = true)
    private LocalDateTime updatedAt; // 정보 수정일시

    @Builder
    public User(String userLoginId, String email, String nickname, String password, Integer age) {
        this.userLoginId = userLoginId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.age = age;
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user")); // 우선 유저 반환
    }

    // 사용자의 id를 반환
    @Override
    public String getUsername() {
        return userLoginId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료 확인 로직
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금 확인 로직
        return true;
    }

    // 패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료 확인 로직
        return true;
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
