package com.goorm.jido.service;

import com.goorm.jido.config.CustomUserDetails;
import com.goorm.jido.entity.User;
import com.goorm.jido.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 사용자 인증에 사용되는 UserDetailsService 구현체
 */
@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * 주어진 사용자 ID(userLoginId)로 DB에서 사용자 정보를 조회하고
   * UserDetails 객체로 반환한다.
   *
   * @param userLoginId 클라이언트가 로그인 시 입력한 ID
   * @return UserDetails 객체 (Spring Security에서 인증에 사용)
   * @throws UsernameNotFoundException 사용자가 없을 경우 인증 실패 처리
   */
  @Override
  public UserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {
    User user = userRepository.findByUserLoginId(userLoginId)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userLoginId));
    return new CustomUserDetails(user);
  }
}