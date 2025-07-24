import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StepContentRepository extends JpaRepository<StepContent, Long> {
    List<StepContent> findByStepIdOrderByCreatedAtAsc(Long stepId);
}
