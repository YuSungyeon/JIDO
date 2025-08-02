package com.goorm.jido_.Controller;

import com.goorm.jido_.DTO.SignupRequestDto;
import com.goorm.jido_.DTO.SignupResponseDto;
import com.goorm.jido_.DTO.UserResponseDto;
import com.goorm.jido_.Entitiy.User;
import com.goorm.jido_.SecurityConfig.CustomLogoutSuccessHandler;
import com.goorm.jido_.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;
  private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

  @GetMapping("/user/{nickname}")
  public ResponseEntity<UserResponseDto> findBynickname(@PathVariable String nickname) {
    try{
      User user = userService.findByNickname(nickname);
      return ResponseEntity.status(HttpStatus.OK)
              .body(new UserResponseDto(user));

    }catch (IllegalArgumentException e){
      return ResponseEntity.notFound().build();
    }
  }

  // 회원 가입
  @PostMapping("/user")
  public ResponseEntity<SignupResponseDto> signUp(SignupRequestDto request){

    SignupResponseDto signupResponseDto = new SignupResponseDto(
            userService.save(request), // userId
            "signup successful"
    );

    return ResponseEntity.ok(signupResponseDto);
  }

  // 로그 아웃
  @GetMapping("/api/logout")
  public void logout(HttpServletRequest request,
                     HttpServletResponse response) throws IOException, ServletException {
    // 1. 인증 객체
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    // 2. 로그아웃 수행
    new SecurityContextLogoutHandler().logout(request, response, authentication);

    // 3. 로그아웃 핸들러 호출
    customLogoutSuccessHandler.onLogoutSuccess(request, response, authentication);
  }
}
