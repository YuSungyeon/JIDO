package com.goorm.jido_.Controller;

import com.goorm.jido_.DTO.UserResponseDto;
import com.goorm.jido_.Entitiy.User;
import com.goorm.jido_.Repository.CategoryRepository;
import com.goorm.jido_.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  @PostMapping("/user/{nickname}")
  public ResponseEntity<UserResponseDto> findBynickname(@PathVariable String nickname) {
    return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.findByNickname(nickname));
  }

}
