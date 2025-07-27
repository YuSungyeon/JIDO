package com.goorm.jido_.Service;
import com.goorm.jido_.Entitiy.User;
import com.goorm.jido_.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User findByNickname(String nickname) { // 닉네임으로 유저 검색
    return userRepository.findBynickname(nickname);
  }
}
