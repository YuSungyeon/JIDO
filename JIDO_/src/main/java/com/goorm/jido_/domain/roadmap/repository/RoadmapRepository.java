import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    List<Roadmap> findByAuthorId(Long authorId);
    List<Roadmap> findByCategory(String category);
    List<Roadmap> findByIsPublicTrue();  // 공개 로드맵만 조회
}
