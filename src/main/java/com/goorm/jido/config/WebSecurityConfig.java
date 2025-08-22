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

  private final UserDetailService userService;

  @Bean
  public WebSecurityCustomizer configure() {
    return web -> web.ignoring().requestMatchers("/static/**");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
                                         CustomAuthenticationSuccessHandler successHandler,
                                         CustomAuthenticationFailureHandler failureHandler,
                                         AuthenticationManager authenticationManager
  ) throws Exception {

    JsonUsernamePasswordAuthenticationFilter jsonFilter = new JsonUsernamePasswordAuthenticationFilter(authenticationManager);
    jsonFilter.setFilterProcessesUrl("/api/login");
    jsonFilter.setAuthenticationSuccessHandler(successHandler);
    jsonFilter.setAuthenticationFailureHandler(failureHandler);

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
  public AuthenticationManager authenticationManagerBean(BCryptPasswordEncoder bCryptPasswordEncoder) {
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
