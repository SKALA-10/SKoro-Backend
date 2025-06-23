package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skala.skoro.domain.employee.dto.RankedNonFinalEvaluationProjection;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.evaluation.entity.FeedbackReport;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import skala.skoro.domain.kpi.dto.MyContributionResponse;
import java.util.List;
import java.util.Optional;

public interface FeedbackReportRepository extends JpaRepository<FeedbackReport, Long> {
    @Query(value = """
    SELECT 
            e.emp_no AS empNo,
            e.emp_name AS empName,
            e.profile_image AS profileImage,
            e.position AS position,
            f.contribution_rate AS contributionRate,
            f.ai_achievement_rate AS aiAchievementRate,
            f.attitude AS attitude,
            RANK() OVER (ORDER BY f.ai_achievement_rate DESC) AS ranking
        FROM feedback_reports f
        JOIN employees e ON f.emp_no = e.emp_no
        WHERE f.team_evaluation_id = :teamEvaluationId AND e.role != 'MANAGER'
    """, nativeQuery = true)
    List<RankedNonFinalEvaluationProjection> findRankedNonFinalEvaluationsByTeamEvaluationId(@Param("teamEvaluationId") Long teamEvaluationId);

    Optional<FeedbackReport> findByTeamEvaluationAndEmployee(TeamEvaluation teamEvaluation, Employee employee);

    @Query("""
        select new skala.skoro.domain.kpi.dto.MyContributionResponse(
            p.year, p.orderInYear, avg(f.contributionRate)
        )
        from FeedbackReport f
        join TeamEvaluation te on f.teamEvaluation.id = te.id
        join Period p on te.period.id = p.id
        where f.employee.empNo = :empNo
        group by p.year, p.orderInYear
        order by p.year, p.orderInYear
    """)
    List<MyContributionResponse> findContributionByEmpNoGrouped(@Param("empNo") String empNo);
}
