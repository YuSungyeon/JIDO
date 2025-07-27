package com.goorm.jido_.Controller;

import com.goorm.jido_.Entitiy.User;
import com.goorm.jido_.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/{nickname}")
  public User findBynickname(@PathVariable String nickname) {
    return userService.findByNickname(nickname);
  }
}
