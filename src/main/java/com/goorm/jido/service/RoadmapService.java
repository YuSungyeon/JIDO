package com.goorm.jido.service;

import com.goorm.jido.dto.RoadmapUpdateRequestDto;
import com.goorm.jido.entity.Roadmap;
import com.goorm.jido.entity.User;
import com.goorm.jido.repository.RoadmapRepository;
import com.goorm.jido.repository.UserRepository;
import com.goorm.jido.dto.RoadmapResponseDto;
import com.goorm.jido.dto.RoadmapRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;

    // 로드맵 생성
    public RoadmapResponseDto saveRoadmap(RoadmapRequestDto dto, Long userId) {
        Long finalAuthorId = (userId != null) ? userId : dto.authorId();
        if (finalAuthorId == null) throw new IllegalArgumentException("authorId 또는 로그인 필요");
        if (dto.title() == null || dto.title().isBlank()) throw new IllegalArgumentException("title 필수");
        if (dto.description() == null || dto.description().isBlank()) throw new IllegalArgumentException("description 필수");
        if (dto.category() == null || dto.category().isBlank()) throw new IllegalArgumentException("category 필수");

        User author = userRepository.findById(finalAuthorId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + finalAuthorId));

        LocalDateTime now = LocalDateTime.now();

        Roadmap roadmap = Roadmap.builder()
                .author(author)
                .title(dto.title())
                .description(dto.description())
                .category(dto.category())
                .isPublic(Boolean.TRUE.equals(dto.isPublic()))
                .createdAt(now)
                .updatedAt(now)
                .build();

        // ✅ 섹션 저장 반영 (DTO의 섹션 제목 리스트를 엔티티로 변환)
        roadmap.replaceSectionsByTitles(dto.sections());

        Roadmap saved = roadmapRepository.save(roadmap);

        return RoadmapResponseDto.from(saved, 0L, false, 0L, false);
    }


    // 특정 로드맵 조회
    @Transactional(readOnly = true)
    public Optional<RoadmapResponseDto> getRoadmap(Long id, Long userId) {
        return roadmapRepository.findById(id)
                .map(r -> RoadmapResponseDto.from(r, 0L, false, 0L, false));
    }

    // 전체 로드맵 조회
    @Transactional(readOnly = true)
    public List<RoadmapResponseDto> getAllRoadmaps(Long userId) {
        return roadmapRepository.findAll().stream()
                .map(r -> RoadmapResponseDto.from(r, 0L, false, 0L, false))
                .toList();
    }

    // 로드맵 삭제
    public void deleteRoadmap(Long id) {
        roadmapRepository.deleteById(id);
    }

    @Transactional
    public RoadmapResponseDto updateRoadmap(Long id, Long userId, RoadmapUpdateRequestDto dto) {
        Roadmap roadmap = roadmapRepository.findByRoadmapIdAndAuthor_UserId(id, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "로드맵이 없거나 권한이 없습니다."));

        roadmap.updateBasicInfo(dto.title(), dto.description(), dto.category(), dto.isPublic());

        if (dto.sections() != null) {
            roadmap.replaceSectionsByTitles(dto.sections());  // ✅ get 없이 한 방에 처리
        }

        return RoadmapResponseDto.from(roadmap, 0L, false, 0L, false); // 기존 매퍼/팩토리 유지
    }


}