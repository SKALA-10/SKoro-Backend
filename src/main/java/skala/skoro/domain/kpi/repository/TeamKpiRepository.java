package skala.skoro.domain.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.employee.entity.Team;
import skala.skoro.domain.kpi.entity.TeamKpi;
import java.util.List;

public interface TeamKpiRepository extends JpaRepository<TeamKpi, Long> {
    List<TeamKpi> findByTeamAndYearOrderByWeightDesc(Team team, int year);
}
