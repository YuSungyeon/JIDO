package com.goorm.jido_.Service;
import com.goorm.jido_.DAO.User;
import com.goorm.jido_.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> findAll() {
    return userRepository.findAll();
  }
}
