package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.PeerEvaluationKeyword;

public interface PeerEvaluationKeywordRepository extends JpaRepository<PeerEvaluationKeyword, Long> {
}
