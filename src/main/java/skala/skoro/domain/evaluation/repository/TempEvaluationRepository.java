package skala.skoro.domain.evaluation.repository;

import org.springframework.data.repository.CrudRepository;
import skala.skoro.domain.evaluation.entity.TempEvaluation;

public interface TempEvaluationRepository extends CrudRepository<TempEvaluation, String> {
}