package com.goorm.jido.service;

import com.goorm.jido.dto.StepContentUpdateRequestDto;
import com.goorm.jido.entity.StepContent;
import com.goorm.jido.repository.StepContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StepContentService {
    private final StepContentRepository stepContentRepository;

    public StepContent saveStepContent(StepContent stepContent) {
        return stepContentRepository.save(stepContent);
    }

    public Optional<StepContent> getStepContent(Long id) {
        return stepContentRepository.findById(id);
    }

    public List<StepContent> getAllStepContents() {
        return stepContentRepository.findAll();
    }

    public void deleteStepContent(Long id) {
        stepContentRepository.deleteById(id);
    }

    // 스텝 콘텐츠 수정 (작성자 본인만)
    @Transactional
    public StepContent updateStepContent(Long stepContentId, Long userId, StepContentUpdateRequestDto dto) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required");
        }
        StepContent sc = stepContentRepository
                .findByStepContentIdAndStep_RoadmapSection_Roadmap_Author_UserId(stepContentId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "컨텐츠가 없거나 권한이 없습니다."));

        sc.update(dto.content(), dto.finished()); // 엔티티 도메인 메서드
        return sc;
    }
}
