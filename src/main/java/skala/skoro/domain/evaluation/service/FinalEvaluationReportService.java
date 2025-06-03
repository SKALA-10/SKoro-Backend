package skala.skoro.domain.evaluation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.service.EmployeeService;
import skala.skoro.domain.evaluation.dto.FinalEvaluationReportResponse;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import skala.skoro.domain.evaluation.repository.FinalEvaluationReportRepository;
import skala.skoro.domain.evaluation.repository.TeamEvaluationRepository;
import skala.skoro.global.exception.CustomException;

import static skala.skoro.global.exception.ErrorCode.FINAL_EVALUATION_REPORT_DOES_NOT_EXIST;
import static skala.skoro.global.exception.ErrorCode.TEAM_EVALUATION_DOES_NOT_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class FinalEvaluationReportService {

    private final EmployeeService employeeService;

    private final FinalEvaluationReportRepository finalEvaluationReportRepository;

    private final TeamEvaluationRepository teamEvaluationRepository;

    @Transactional(readOnly = true)
    public FinalEvaluationReportResponse getFinalEvaluationReport(String empNo, Long periodId) {
        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        TeamEvaluation teamEvaluation = teamEvaluationRepository
                .findByTeamAndPeriodId(employee.getTeam(), periodId)
                .orElseThrow(() -> new CustomException(TEAM_EVALUATION_DOES_NOT_EXIST));

        return finalEvaluationReportRepository
                .findByTeamEvaluationAndEmployee(teamEvaluation, employee)
                .map(FinalEvaluationReportResponse::from)
                .orElseThrow(() -> new CustomException(FINAL_EVALUATION_REPORT_DOES_NOT_EXIST));
    }
}
