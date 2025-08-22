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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  // 필요한 핸들러와 서비스를 필드로 주입
  private final UserDetailService userService;
  private final CustomAuthenticationSuccessHandler successHandler;
  private final CustomAuthenticationFailureHandler failureHandler;

  // 정적 리소스는 보안 필터를 거치지 않도록 설정
  @Bean
  public WebSecurityCustomizer configure() {
    return web -> web.ignoring().requestMatchers("/static/**");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JsonUsernamePasswordAuthenticationFilter jsonFilter) throws Exception {
    return http
            .cors(c -> {})
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/login", "/static/**", "/users", "/swagger-ui/**","/v3/api-docs/**").permitAll() // 로그인은 허용
                    .anyRequest().authenticated() // 나머지는 인증 필요
            )
            .addFilterAt(jsonFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
  public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
    JsonUsernamePasswordAuthenticationFilter jsonFilter = new JsonUsernamePasswordAuthenticationFilter(authenticationManager);
    jsonFilter.setFilterProcessesUrl("/api/login");
    jsonFilter.setAuthenticationSuccessHandler(successHandler);
    jsonFilter.setAuthenticationFailureHandler(failureHandler);
    return jsonFilter;
  }

  @Bean
  public AuthenticationManager authenticationManager(BCryptPasswordEncoder bCryptPasswordEncoder) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(bCryptPasswordEncoder);
    return new ProviderManager(authProvider);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
