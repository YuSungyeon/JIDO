package com.goorm.jido.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
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
}
