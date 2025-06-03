package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.evaluation.entity.FeedbackReport;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FeedbackReportRepository extends JpaRepository<FeedbackReport, Long> {
    List<FeedbackReport> findByTeamEvaluationIdOrderByRankingAsc(Long teamEvaluationId);

    Optional<FeedbackReport> findByTeamEvaluationAndEmployee(TeamEvaluation teamEvaluation, Employee employee);
}
