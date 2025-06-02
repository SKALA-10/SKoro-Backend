package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.FinalEvaluationReport;

public interface FinalEvaluationReportRepository extends JpaRepository<FinalEvaluationReport, Long> {
}
