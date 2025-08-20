// 파일명: CustomUserDetails.java
package com.goorm.jido.config;

import com.goorm.jido.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getUserId(); // 여기에 접근할 필드를 선택
    }

    @Override
    public String getUsername() {
        return user.getUserLoginId(); // 로그인에 사용하는 ID
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 반드시 암호화된 비밀번호여야 함
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한이 없다면 빈 리스트 반환
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}