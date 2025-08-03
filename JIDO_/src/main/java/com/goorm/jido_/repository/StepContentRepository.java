package com.goorm.jido_.repository;

import com.goorm.jido_.entity.StepContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepContentRepository extends JpaRepository<StepContent, Long> {
    // 필요 시 커스텀 쿼리 메소드 정의 가능
}
