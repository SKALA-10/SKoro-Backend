package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.employee.entity.Team;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;

import java.util.Optional;

public interface TeamEvaluationRepository extends JpaRepository<TeamEvaluation, Long> {
    Optional<TeamEvaluation> findByTeamAndPeriodId(Team team, Long periodId);

    Optional<TeamEvaluation> findByPeriodIdAndTeam(Long periodId, Team team);
}
