package skala.skoro.domain.evaluation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skala.skoro.domain.employee.dto.RankedFinalEvaluationProjection;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.evaluation.entity.FinalEvaluationReport;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import skala.skoro.domain.kpi.dto.MyFinalScoreResponse;

import java.util.List;
import java.util.Optional;

public interface FinalEvaluationReportRepository extends JpaRepository<FinalEvaluationReport, Long> {
    @Query(value = """
        SELECT 
            e.emp_no AS empNo,
            e.emp_name AS empName,
            e.profile_image AS profileImage,
            e.position AS position,
            f.contribution_rate AS contributionRate,
            f.ai_annual_achievement_rate AS aiAnnualAchievementRate,
            f.score AS score,
            RANK() OVER (ORDER BY f.score DESC) AS ranking
        FROM final_evaluation_reports f
        JOIN employees e ON f.emp_no = e.emp_no
        WHERE f.team_evaluation_id = :teamEvaluationId AND e.role != 'MANAGER'
    """, nativeQuery = true)
    List<RankedFinalEvaluationProjection> findRankedFinalEvaluationsByTeamEvaluationId(@Param("teamEvaluationId") Long teamEvaluationId);

    Optional<FinalEvaluationReport> findByTeamEvaluationAndEmployee(TeamEvaluation teamEvaluation, Employee employee);

    @Query("""
        select new skala.skoro.domain.kpi.dto.MyFinalScoreResponse(
            p.year,
            max(f.score)
        )
        from FinalEvaluationReport f
        join f.teamEvaluation te
        join te.period p
        where f.employee.empNo = :empNo
        group by p.year
        order by p.year
    """)
    List<MyFinalScoreResponse> findFinalScoreByEmpNoGrouped(@Param("empNo") String empNo);
}
