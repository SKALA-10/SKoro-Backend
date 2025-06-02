package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.PeerEvaluation;

public interface PeerEvaluationRepository extends JpaRepository<PeerEvaluation, Long> {
}
