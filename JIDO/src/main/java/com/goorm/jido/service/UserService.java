package com.goorm.jido.service;
import com.goorm.jido.dto.SignupRequestDto;
import com.goorm.jido.entity.User;
import com.goorm.jido.entity.userInterest.UserInterest;
import com.goorm.jido.repository.CategoryRepository;
import com.goorm.jido.repository.UserInterestRepository;
import com.goorm.jido.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserInterestRepository userInterestRepository;
  private final CategoryRepository categoryRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public User findByNickname(String nickname) { // 닉네임으로 유저 검색
    return userRepository.findByNickname(nickname)
            .orElseThrow(() -> new IllegalArgumentException());
  }

  public Long save(SignupRequestDto dto) {
    // 1. 유저 정보 저장
    User saved =  userRepository.save(User.builder()
            .userLoginId(dto.getUserLoginId())
            .password(bCryptPasswordEncoder.encode(dto.getPassword()))
            .email(dto.getEmail())
            .nickname(dto.getNickName())
            .age(dto.getAge())
            .build()
    );


    // 2. 유저 관심 카테고리 저장
    for (String category : dto.getCategories()){
      System.out.println(category);
      userInterestRepository.save(UserInterest.builder()
              .user(saved)
              .category(categoryRepository.findByCategoryId(category).orElseThrow(() -> new IllegalArgumentException("dddddddd")))
              .build()
      );
    }

    return  saved.getUserId();

  }
}
