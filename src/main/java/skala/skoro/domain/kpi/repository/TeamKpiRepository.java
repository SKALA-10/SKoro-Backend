package skala.skoro.domain.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skala.skoro.domain.employee.entity.Team;
import skala.skoro.domain.kpi.entity.TeamKpi;
import java.util.List;

public interface TeamKpiRepository extends JpaRepository<TeamKpi, Long> {
    List<TeamKpi> findByTeamAndYearOrderByWeightDesc(Team team, int year);

    List<TeamKpi> findByTeam_Id(Long teamId);

    @Query("""
        SELECT DISTINCT t.teamKpi
        FROM Task t
        WHERE t.employee.empNo = :empNo
          AND t.teamKpi.year = :year
    """)
    List<TeamKpi> findTeamKpisByEmpNoAndYear(String empNo, int year);
}
