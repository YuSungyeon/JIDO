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
    //config.setExposedHeaders(List.of("Authorization", "Content-Type"));
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
                    .requestMatchers(HttpMethod.GET,
                            "/roadmaps",
                            "/roadmaps/**",
                            "/sections",
                            "/sections/**",
                            "/steps",
                            "/steps/**",
                            "/step-contents",
                            "/step-contents/**",
                            "/categories",
                            "/user",
                            "/user/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/roadmaps/**").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/sections/**", "/steps/**", "/step-contents/**").authenticated()
                    // .requestMatchers(HttpMethod.PUT, "/api/roadmaps/**").authenticated()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/api/login", "/user", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
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