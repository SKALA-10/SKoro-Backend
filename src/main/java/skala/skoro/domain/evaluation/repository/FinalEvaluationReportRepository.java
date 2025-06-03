package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.FinalEvaluationReport;

import java.util.Collection;
import java.util.List;

public interface FinalEvaluationReportRepository extends JpaRepository<FinalEvaluationReport, Long> {
    List<FinalEvaluationReport> findByTeamEvaluationIdOrderByRankingAsc(Long teamEvaluationId);
}
