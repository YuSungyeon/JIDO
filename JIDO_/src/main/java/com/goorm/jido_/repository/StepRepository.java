package com.goorm.jido_.repository;

import com.goorm.jido_.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
    // 필요 시 커스텀 쿼리 메소드 정의 가능
}
