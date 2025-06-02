package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.ManagerEvaluation;

public interface ManagerEvaluationRepository extends JpaRepository<ManagerEvaluation, Long> {
}
