package com.goorm.jido.config;


import com.goorm.jido.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UserDetailService userService;

  // 1. 스프링 시큐리티 기능 비활성화
  @Bean
  public WebSecurityCustomizer configure(){
    return (web) -> web.ignoring()
            .requestMatchers("/static/**"); // 정적 리소스에 대해 스프링 시큐리티 사용 비활성화
  }

  // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
                                         CustomAuthenticationSuccessHandler successHandler,
                                         CustomAuthenticationFailureHandler failureHandler
                                         ) throws Exception {
    return http
            // 모든 페이지 시큐리티 해제
             .authorizeHttpRequests(auth -> auth
                      .anyRequest().permitAll()
             )
            .formLogin(formLogin -> formLogin // 폼 기반 로그인 설정
//                    // 로그인 페이지 설정 X -> Spring Security 기본 제공 로그인 페이지(/login)
//                    // "/login" 에서 로그인 시, POST "/api/login" 으로 전송
//
                    .loginProcessingUrl("/api/login") // 사용 시, "/api/login"으로 폼 데이터 전송(username, password)
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
            )

            .logout(logout -> logout
                    .logoutUrl("/none") // 시큐리티 기본 로그아웃 비활성화
            )
            .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
            .build();

  } // end of filterChain Method


  // 3. 인증 관리자 관련 설정
  @Bean
  public AuthenticationManager authenticationManagerBean(BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {

    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService); // 사용자 정보 서비스 설정
    authProvider.setPasswordEncoder(bCryptPasswordEncoder);
    return new ProviderManager(authProvider);
  }

  // 4. 패스워드 인코더로 사용할 빈 등록
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
