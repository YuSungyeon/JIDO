package com.goorm.jido.service;

import com.goorm.jido.dto.StepUpdateRequestDto;
import com.goorm.jido.entity.Step;
import com.goorm.jido.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StepService {
    private final StepRepository stepRepository;

    public Step saveStep(Step step) {
        return stepRepository.save(step);
    }

    public Optional<Step> getStep(Long id) {
        return stepRepository.findById(id);
    }

    public List<Step> getAllSteps() {
        return stepRepository.findAll();
    }

    public void deleteStep(Long id) {
        stepRepository.deleteById(id);
    }

    // 스텝 수정 (작성자 본인만)
    @Transactional
    public Step updateStep(Long stepId, Long userId, StepUpdateRequestDto dto) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required");
        }
        Step step = stepRepository
                .findByStepIdAndRoadmapSection_Roadmap_Author_UserId(stepId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "스텝이 없거나 권한이 없습니다."));

        step.update(dto.title(), dto.stepNumber()); // 엔티티 도메인 메서드
        return step;
    }
}
