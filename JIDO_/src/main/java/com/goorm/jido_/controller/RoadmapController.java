package com.goorm.jido_.controller;

import com.goorm.jido_.dto.RoadmapRequestDto;
import com.goorm.jido_.entity.Roadmap;
import com.goorm.jido_.entity.User;
import com.goorm.jido_.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping({"/roadmaps", "/api/roadmaps"}) // 두 경로 모두 허용
@RequiredArgsConstructor
@Slf4j
public class RoadmapController {

    private final RoadmapService roadmapService;

    // 내가 작성한 로드맵 조회
    @GetMapping("/my")
    public List<Roadmap> getMyRoadmaps(@AuthenticationPrincipal User user) {
        return roadmapService.getMyRoadmaps(user);
    }

    // 로드맵 생성 (프린시펄 타입 자동 대응 + 4xx로 의미 있게 응답)
    @PostMapping(path = {"", "/"}, consumes = "application/json", produces = "application/json")
    public Roadmap create(@RequestBody RoadmapRequestDto dto,
                          @AuthenticationPrincipal Object principal) {

        // 어떤 타입의 principal이 들어왔는지 로그로 남겨 원인 고정
        log.info("POST /roadmaps principalClass={}, dto={}",
                principal == null ? "null" : principal.getClass().getName(), dto);

        Long userId = extractUserId(principal);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요하거나 사용자 ID 추출 실패");
        }

        try {
            return roadmapService.saveRoadmap(dto, userId); // author는 userId로 고정
        } catch (DataIntegrityViolationException e) {
            // NOT NULL / FK 제약 등 데이터 무결성 문제 → 400으로 안내
            log.error("Data integrity error on saveRoadmap", e);
            String msg = e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage() : e.getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "데이터 제약 위반: " + msg);
        } catch (TransientPropertyValueException e) {
            // 부모-자식 연관 세팅 누락 등 → 400
            log.error("TransientPropertyValueException on saveRoadmap", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "연관관계 설정 누락(부모 참조 필요)");
        }
    }

    // 특정 로드맵 조회
    @GetMapping("/{id}")
    public Roadmap get(@PathVariable Long id) {
        return roadmapService.getRoadmap(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "roadmap not found: " + id));
    }

    // 전체 로드맵 조회
    @GetMapping
    public List<Roadmap> getAll() {
        return roadmapService.getAllRoadmaps();
    }

    // 로드맵 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roadmapService.deleteRoadmap(id);
    }

    /**
     * principal에서 userId를 안전하게 추출 (여러 보안 구성 대응)
     */
    private Long extractUserId(Object principal) {
        if (principal == null) return null;

        // 1) 도메인 User가 그대로 principal인 경우
        if (principal instanceof com.goorm.jido_.entity.User u) {
            return u.getUserId();
        }

        // 2) CustomUserDetails 계층을 쓰는 일반적인 경우
        try {
            if (principal instanceof com.goorm.jido_.security.CustomUserDetails cud) {
                return cud.getUserId();
            }
        } catch (NoClassDefFoundError ignored) {
            // 프로젝트에 해당 클래스가 없을 수도 있으니 무시
        }

        // 3) JWT를 쓰는 경우 (claim 키는 프로젝트 설정에 맞게 조정)
        try {
            if (principal instanceof org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken jwt) {
                Object claim = jwt.getToken().getClaims().get("userId"); // 예: "sub", "id" 등일 수 있음
                if (claim != null) return Long.valueOf(claim.toString());
            }
        } catch (Exception ignored) {
        }

        return null;
    }
}
