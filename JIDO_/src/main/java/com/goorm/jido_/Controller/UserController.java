package com.goorm.jido_.Controller;

import com.goorm.jido_.DAO.User;
import com.goorm.jido_.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController //
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/test")
  public List<User> getAllMembers() {
    return userService.findAll();
  }
}
