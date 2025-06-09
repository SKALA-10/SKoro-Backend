package skala.skoro.domain.kpi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.service.EmployeeService;
import skala.skoro.domain.kpi.dto.EmployeeSimple;
import skala.skoro.domain.kpi.dto.TeamKpiDetailResponse;
import skala.skoro.domain.kpi.repository.TaskRepository;
import skala.skoro.domain.kpi.repository.TeamKpiRepository;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamKpiService {

    private final EmployeeService employeeService;

    private final TeamKpiRepository teamKpiRepository;

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<TeamKpiDetailResponse> getTeamKpis(String empNo) {
        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        return teamKpiRepository.findByTeamAndYearOrderByWeightDesc(employee.getTeam(), LocalDate.now().getYear()).stream()
                .map(kpi -> {
                    List<EmployeeSimple> participants = taskRepository.findEmployeesByTeamKpiId(kpi.getId()).stream()
                            .map(EmployeeSimple::from)
                            .toList();
                    return TeamKpiDetailResponse.of(kpi, participants);
                })
                .toList();
    }
}
