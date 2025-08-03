package com.goorm.jido_.repository;

import com.goorm.jido_.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findBynickname(String nickname); // 닉네임으로 유저 검색
}