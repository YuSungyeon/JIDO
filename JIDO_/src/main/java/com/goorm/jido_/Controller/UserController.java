package com.goorm.jido_.Controller;

import com.goorm.jido_.DTO.SignupRequestDto;
import com.goorm.jido_.DTO.SignupResponseDto;
import com.goorm.jido_.DTO.UserResponseDto;
import com.goorm.jido_.Entitiy.User;
import com.goorm.jido_.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

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

  // 회원 가입 기능
  @PostMapping("/user")
  public ResponseEntity<SignupResponseDto> signUp(SignupRequestDto request){

    SignupResponseDto signupResponseDto = new SignupResponseDto(
            userService.save(request), // userId
            "signup successful"
    );

    return ResponseEntity.ok(signupResponseDto);
  }

}
