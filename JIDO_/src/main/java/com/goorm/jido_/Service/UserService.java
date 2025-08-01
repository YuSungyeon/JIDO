package com.goorm.jido_.Service;
import com.goorm.jido_.DTO.UserResponseDto;
import com.goorm.jido_.Entitiy.User;
import com.goorm.jido_.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  public User findByNickname(String nickname) { // 닉네임으로 유저 검색
    return userRepository.findByNickname(nickname)
            .orElseThrow(() -> new IllegalArgumentException());
  }
}
