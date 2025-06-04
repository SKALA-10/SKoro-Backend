package skala.skoro.domain.kpi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.service.EmployeeService;
import skala.skoro.domain.kpi.dto.TeamKpiDetailResponse;
import skala.skoro.domain.kpi.repository.TeamKpiRepository;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamKpiService {

    private final EmployeeService employeeService;

    private final TeamKpiRepository teamKpiRepository;

    public List<TeamKpiDetailResponse> getTeamKpis() {
        String empNo = "E001"; // TODO

        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        return teamKpiRepository.findByTeamAndYearOrderByProgressDesc(employee.getTeam(), LocalDate.now().getYear()).stream()
                .map(TeamKpiDetailResponse::from)
                .toList();
    }
}
