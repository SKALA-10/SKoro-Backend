package skala.skoro.domain.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.ai.entity.Prompt;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
}
