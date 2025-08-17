package com.goorm.jido_.service;

import com.goorm.jido_.dto.RoadmapRequestDto;
import com.goorm.jido_.entity.Roadmap;
import com.goorm.jido_.entity.User;
import com.goorm.jido_.repository.RoadmapRepository;
import com.goorm.jido_.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;

    /**
     * 컨트롤러에서 전달한 인증 사용자 ID(userId)를 사용하여 author를 설정.
     * dto의 authorId는 무시(임시/이전 호환 로직 제거).
     */
    @Transactional
    public Roadmap saveRoadmap(RoadmapRequestDto dto, Long userId) {
        // 1) 인증 사용자 로드 (없으면 401 수준으로 응답)
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "인증 사용자 정보를 찾을 수 없습니다. id=" + userId));

        // 2) 로드맵 생성 (섹션/스텝이 있다면 여기서 연관관계(parent)도 함께 세팅 필요)
        Roadmap roadmap = Roadmap.builder()
                .author(author)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())     // enum이면 매핑/검증 필요
                .isPublic(Boolean.TRUE.equals(dto.getIsPublic()))
                .build();

        // TODO: dto에 sections/steps가 있다면
        // - section.setRoadmap(roadmap);
        // - step.setSection(section);
        // 를 설정하고 CascadeType.ALL이도록 엔티티 매핑 확인

        // 3) 저장
        return roadmapRepository.save(roadmap);
    }

    public Optional<Roadmap> getRoadmap(Long id) {
        return roadmapRepository.findById(id);
    }

    public List<Roadmap> getAllRoadmaps() {
        return roadmapRepository.findAll();
    }

    public List<Roadmap> getMyRoadmaps(User user) {
        return roadmapRepository.findByAuthor_UserId(user.getUserId());
    }

    public void deleteRoadmap(Long id) {
        roadmapRepository.deleteById(id);
    }
}
