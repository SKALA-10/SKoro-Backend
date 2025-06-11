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

    // 올해 시작~종료가 걸친 본인 Task 전체 조회
    @Query("""
        select t from Task t
        join fetch t.teamKpi k
        where t.employee.empNo = :empNo
          and (YEAR(t.startDate) = :year or YEAR(t.endDate) = :year)
    """)
    List<Task> findMyTasksThisYear(@Param("empNo") String empNo, @Param("year") int year);

    // 팀 내 동일 Task 조회
    List<Task> findByTaskNameAndTeamKpi_Id(String taskName, Long teamKpiId);

    // 특정 팀 KPI를 할당받은 사원 empNo 리스트
    @Query("select t.employee.empNo from Task t where t.teamKpi.id = :teamKpiId")
    List<String> findEmpNosByTeamKpiId(@Param("teamKpiId") Long teamKpiId);
}
