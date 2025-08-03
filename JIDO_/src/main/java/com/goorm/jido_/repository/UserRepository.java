package com.goorm.jido_.repository;

import com.goorm.jido_.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByNickname(String nickname); // 닉네임으로 유저 검색
  Optional<User> findByUserLoginId(String userLoginId);
}