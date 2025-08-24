package com.goorm.jido.config;

import com.goorm.jido.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UserDetailService userService;
  private final CustomAuthenticationSuccessHandler successHandler;
  private final CustomAuthenticationFailureHandler failureHandler;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring()
            .requestMatchers(
                    PathRequest.toStaticResources().atCommonLocations()
            );
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setExposedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 JsonUsernamePasswordAuthenticationFilter jsonFilter) throws Exception {
    return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/roadmaps/**").permitAll()
                    .requestMatchers("/api/login", "/users", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterAt(jsonFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  /**
   * AuthenticationManager는 AuthenticationConfiguration을 통해 생성
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * JSON 로그인 필터 등록 (AuthenticationManager 주입)
   */
  @Bean
  public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter(
          AuthenticationManager authenticationManager) {
    JsonUsernamePasswordAuthenticationFilter jsonFilter = new JsonUsernamePasswordAuthenticationFilter(authenticationManager);
    jsonFilter.setFilterProcessesUrl("/api/login");
    jsonFilter.setAuthenticationSuccessHandler(successHandler);
    jsonFilter.setAuthenticationFailureHandler(failureHandler);
    return jsonFilter;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

//package com.goorm.jido.config;
//
//import com.goorm.jido.service.UserDetailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//  private final UserDetailService userService;
//  private final CustomAuthenticationSuccessHandler successHandler;
//  private final CustomAuthenticationFailureHandler failureHandler;
//
//  // 정적 리소스 등 보안 적용 제외할 경로 설정
//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return web -> web.ignoring().requestMatchers("/static/**");
//  }
//
//  // Spring Security 기준 CORS 설정 (중복 방지를 위해 WebMvcConfigurer는 제거)
//  @Bean
//  public CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration config = new CorsConfiguration();
//
//    config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000")); // 프론트 주소
//    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//    config.setAllowedHeaders(List.of("*"));
//    config.setExposedHeaders(List.of("*"));
//    config.setAllowCredentials(true); // ✅ 쿠키 포함 요청 허용
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", config);
//    return source;
//  }
//
//  // 핵심 필터 체인 설정
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http, JsonUsernamePasswordAuthenticationFilter jsonFilter) throws Exception {
//    return http
//            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/api/login", "/users", "/swagger-ui/**", "/v3/api-docs/**", "/static/**").permitAll()
//                    .anyRequest().authenticated()
//            )
//            .addFilterAt(jsonFilter, UsernamePasswordAuthenticationFilter.class) // ✅ JSON 로그인 필터 등록
//            .build();
//  }
//
//  // JSON 기반 로그인 처리 필터
//  @Bean
//  public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
//    JsonUsernamePasswordAuthenticationFilter jsonFilter = new JsonUsernamePasswordAuthenticationFilter(authenticationManager);
//    jsonFilter.setFilterProcessesUrl("/api/login");
//    jsonFilter.setAuthenticationSuccessHandler(successHandler);
//    jsonFilter.setAuthenticationFailureHandler(failureHandler);
//    return jsonFilter;
//  }
//
//  // 인증 관리자 및 비밀번호 인코더 설정
//  @Bean
//  public AuthenticationManager authenticationManager(BCryptPasswordEncoder passwordEncoder) {
//    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//    authProvider.setUserDetailsService(userService);
//    authProvider.setPasswordEncoder(passwordEncoder);
//    return new ProviderManager(authProvider);
//  }
//
//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//}