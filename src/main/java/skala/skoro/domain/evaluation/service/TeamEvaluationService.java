package skala.skoro.domain.evaluation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.service.EmployeeService;
import skala.skoro.domain.evaluation.dto.TeamEvaluationReportResponse;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import skala.skoro.domain.evaluation.repository.TeamEvaluationRepository;
import skala.skoro.global.exception.CustomException;

import static skala.skoro.global.exception.ErrorCode.TEAM_EVALUATION_DOES_NOT_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamEvaluationService {

    private final EmployeeService employeeService;

    private final TeamEvaluationRepository teamEvaluationRepository;

    @Transactional(readOnly = true)
    public TeamEvaluationReportResponse getTeamEvaluationReportByPeriodId(Long periodId) {
        String empNo = "E001"; // TODO

        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        return TeamEvaluationReportResponse.from(findTeamEvaluationByEmployeeAndPeriodId(employee, periodId));
    }

    public TeamEvaluation findTeamEvaluationByEmployeeAndPeriodId(Employee employee, Long periodId) {
        return teamEvaluationRepository.findByTeamAndPeriodId(employee.getTeam(), periodId)
                .orElseThrow(() -> new CustomException(TEAM_EVALUATION_DOES_NOT_EXIST));
    }

}
