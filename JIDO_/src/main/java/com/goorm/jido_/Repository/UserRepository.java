package com.goorm.jido_.Repository;

import com.goorm.jido_.Entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findBynickname(String nickname); // 닉네임으로 유저 검색
}