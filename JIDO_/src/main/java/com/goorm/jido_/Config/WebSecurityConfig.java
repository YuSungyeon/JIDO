package com.goorm.jido_.Config;


import com.goorm.jido_.Service.UserDetailService;
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
                                         CustomAuthenticationFailureHandler failureHandler,
                                         CustomLogoutSuccessHandler logoutSuccessHandler) throws Exception {
    return http
            .authorizeHttpRequests(auth -> auth // 인증, 인가 설정
                    .requestMatchers("/login", "/signup", "/user").permitAll()
                    .anyRequest().authenticated())

            .formLogin(formLogin -> formLogin // 폼 기반 로그인 설정
                    .loginProcessingUrl("/api/login")
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
            )
            .logout(logout -> logout
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .invalidateHttpSession(true)
            )
            .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
            .build();

  } // end of filterChain Method


  // 3. 인증 관리자 관련 설정
  @Bean
  public AuthenticationManager authenticationManagerBean(HttpSecurity http,
                                                         BCryptPasswordEncoder bCryptPasswordEncoder,
                                                         UserDetailService userDetailService) throws Exception {
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
