package skala.skoro.domain.kpi.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.kpi.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // 해당 teamKpi를 가진 Employee 목록 조회
    @Query("""
        SELECT DISTINCT t.employee
        FROM Task t
        WHERE t.teamKpi.id = :teamKpiId
    """)
    List<Employee> findEmployeesByTeamKpiId(@Param("teamKpiId") Long teamKpiId);
}
