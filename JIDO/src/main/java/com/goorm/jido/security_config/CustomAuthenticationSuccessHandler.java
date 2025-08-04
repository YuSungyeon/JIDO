package com.goorm.jido.security_config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.jido.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
// 로그인 성공 시, 작동할 핸들러
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException{
    // 인증된 사용자 정보 꺼내기
    User userDetails = (User) authentication.getPrincipal();

    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json;charset=UTF-8");

    // 필요한 유저 정보만 골라서 응답
    Map<String, Object> result = new HashMap<>();
    result.put("message", "login successful");
    result.put("username", userDetails.getUsername());
    result.put("roles", userDetails.getAuthorities());
    result.put("userId", userDetails.getUserId());

    String json = new ObjectMapper().writeValueAsString(result);
    response.getWriter().write(json);
  }
}
