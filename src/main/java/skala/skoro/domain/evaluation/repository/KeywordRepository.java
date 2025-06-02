package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
