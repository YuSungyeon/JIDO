package com.goorm.jido.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.jido.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

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
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    User user = userDetails.getUser();

    // ✅ 세션 생성
    request.getSession(true);

    // ✅ SecurityContext에 인증 정보 저장 + 세션에 넣기
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);
    request.getSession().setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            context
    );

    log.info("✅ 로그인 성공 - userId={}, username={}, roles={}",
            userDetails.getUserId(),
            userDetails.getUsername(),
            userDetails.getAuthorities());

    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json;charset=UTF-8");

    Map<String, Object> result = new HashMap<>();
    result.put("message", "login successful");
    result.put("username", user.getUserLoginId());
    result.put("roles", userDetails.getAuthorities());
    result.put("userId", user.getUserId());

    String json = new ObjectMapper().writeValueAsString(result);
    response.getWriter().write(json);
  }
}