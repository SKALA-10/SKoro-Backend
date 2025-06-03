package skala.skoro.domain.evaluation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.evaluation.entity.FinalEvaluationReport;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import java.util.List;
import java.util.Optional;

public interface FinalEvaluationReportRepository extends JpaRepository<FinalEvaluationReport, Long> {
    List<FinalEvaluationReport> findByTeamEvaluationIdOrderByRankingAsc(Long teamEvaluationId);

    Optional<FinalEvaluationReport> findByTeamEvaluationAndEmployee(TeamEvaluation teamEvaluation, Employee employee);
}
