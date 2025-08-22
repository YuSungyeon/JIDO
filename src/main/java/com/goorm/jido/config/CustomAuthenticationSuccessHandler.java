package com.goorm.jido.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.jido.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    // ✅ CustomUserDetails로 캐스팅
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    User user = userDetails.getUser(); // 실제 User 엔티티

    // 세션을 강제로 생성하여 Spring Security가 JSESSIONID 쿠키를 응답에 추가하도록
    request.getSession(true);

    // 사용자 정보 로그 출력 (디버깅용) - 성연
    log.info("✅ 로그인 성공 - userId={}, username={}, roles={}",
            userDetails.getUserId(),
            userDetails.getUsername(),
            userDetails.getAuthorities());

    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json;charset=UTF-8");

    // 필요한 유저 정보만 골라서 응답
    Map<String, Object> result = new HashMap<>();
    result.put("message", "login successful");
    result.put("username", user.getUserLoginId()); // 또는 userDetails.getUsername()
    result.put("roles", userDetails.getAuthorities());
    result.put("userId", user.getUserId());

    String json = new ObjectMapper().writeValueAsString(result);
    response.getWriter().write(json);
  }
}