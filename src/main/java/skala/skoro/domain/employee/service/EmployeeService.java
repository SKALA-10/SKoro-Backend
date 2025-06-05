package skala.skoro.domain.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.employee.dto.EmployeeFinalEvaluationResponse;
import skala.skoro.domain.employee.dto.EmployeeNonFinalEvaluationResponse;
import skala.skoro.domain.employee.dto.EmployeeSummaryResponse;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.entity.Team;
import skala.skoro.domain.employee.repository.EmployeeRepository;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import skala.skoro.domain.evaluation.repository.FeedbackReportRepository;
import skala.skoro.domain.evaluation.repository.FinalEvaluationReportRepository;
import skala.skoro.domain.evaluation.repository.TeamEvaluationRepository;
import skala.skoro.domain.period.service.PeriodService;
import skala.skoro.global.exception.CustomException;
import java.util.List;

import static skala.skoro.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final PeriodService periodService;

    private final EmployeeRepository employeeRepository;

    private final TeamEvaluationRepository teamEvaluationRepository;

    private final FinalEvaluationReportRepository finalEvaluationReportRepository;

    private final FeedbackReportRepository feedbackReportRepository;

    @Transactional(readOnly = true)
    public List<EmployeeSummaryResponse> getEmployeesByTeam() {
        String empNo = "E001"; // TODO

        Team team = findEmployeeByEmpNo(empNo).getTeam();

        return employeeRepository.findByTeam(team).stream()
                .map(EmployeeSummaryResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeFinalEvaluationResponse> getFinalEmployeeEvaluationsByPeriod(Long periodId) {
        String empNo = "E001"; // TODO

        if (!periodService.isFinal(periodId)) {
            throw new CustomException(INVALID_FINAL_EVALUATION_REQUEST);
        }

        Team team = findEmployeeByEmpNo(empNo).getTeam();

        TeamEvaluation teamEvaluation = findTeamEvaluationByTeamAndPeriod(team, periodId);

        return finalEvaluationReportRepository.findByTeamEvaluationIdOrderByRankingAsc(teamEvaluation.getId()).stream()
                    .map(EmployeeFinalEvaluationResponse::from)
                    .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeNonFinalEvaluationResponse> getNonFinalEmployeeEvaluationsByPeriod(Long periodId) {
        String empNo = "E001"; // TODO

        if (periodService.isFinal(periodId)) {
            throw new CustomException(INVALID_NON_FINAL_EVALUATION_REQUEST);
        }

        Team team = findEmployeeByEmpNo(empNo).getTeam();

        TeamEvaluation teamEvaluation = findTeamEvaluationByTeamAndPeriod(team, periodId);

        return feedbackReportRepository.findByTeamEvaluationIdOrderByRankingAsc(teamEvaluation.getId()).stream()
                .map(EmployeeNonFinalEvaluationResponse::from)
                .toList();
    }

    public Employee findEmployeeByEmpNo(String empNo){
         return employeeRepository.findById(empNo)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private TeamEvaluation findTeamEvaluationByTeamAndPeriod(Team team, Long periodId) {
        return teamEvaluationRepository.findByTeamAndPeriodId(team, periodId)
                .orElseThrow(() -> new CustomException(TEAM_EVALUATION_DOES_NOT_EXIST));
    }

    public List<Employee> findByTeam(Team team) {
        return employeeRepository.findByTeam(team);
    }


}
