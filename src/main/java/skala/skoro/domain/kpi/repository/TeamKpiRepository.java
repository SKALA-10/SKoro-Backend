package skala.skoro.domain.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.employee.entity.Team;
import skala.skoro.domain.kpi.dto.TeamKpiDetailResponse;
import skala.skoro.domain.kpi.entity.TeamKpi;

import java.util.List;
import java.util.Optional;

public interface TeamKpiRepository extends JpaRepository<TeamKpi, Long> {
    List<TeamKpi> findByTeamAndYearOrderByProgressDesc(Team team, int year);
}
