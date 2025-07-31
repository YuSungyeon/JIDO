package com.goorm.jido_.Service;
import com.goorm.jido_.DTO.UserResponseDto;
import com.goorm.jido_.Entitiy.User;
import com.goorm.jido_.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  public UserResponseDto findByNickname(String nickname) { // 닉네임으로 유저 검색

    Optional<User> user = userRepository.findByNickname(nickname);
    return UserResponseDto.from(user
            .orElseThrow(() -> new IllegalArgumentException())
    );
  }
}
