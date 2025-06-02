package skala.skoro.domain.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.kpi.entity.TeamKpi;

public interface TeamKpiRepository extends JpaRepository<TeamKpi, Long> {
}
