package skala.skoro.domain.evaluation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.service.EmployeeService;
import skala.skoro.domain.evaluation.dto.FinalEvaluationAchievementStatsResponse;
import skala.skoro.domain.evaluation.dto.TeamEvaluationDetailResponse;
import skala.skoro.domain.evaluation.dto.TeamEvaluationReportResponse;
import skala.skoro.domain.evaluation.dto.TeamEvaluationStatusResponse;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import skala.skoro.domain.evaluation.repository.TeamEvaluationRepository;
import skala.skoro.domain.period.repository.PeriodRepository;
import skala.skoro.global.exception.CustomException;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

import static skala.skoro.global.exception.ErrorCode.TEAM_EVALUATION_DOES_NOT_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamEvaluationService {

    private final EmployeeService employeeService;

    private final TeamEvaluationRepository teamEvaluationRepository;

    private final PeriodRepository periodRepository;

    @Transactional(readOnly = true)
    public List<TeamEvaluationDetailResponse> findTeamEvaluationsByYear(String empNo) {
        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        return periodRepository.findByYearOrderByOrderInYearDesc(LocalDate.now().getYear()).stream()
                .map(period -> teamEvaluationRepository.findByTeamAndPeriod(employee.getTeam(), period)
                        .map(TeamEvaluationDetailResponse::from))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeamEvaluationReportResponse getTeamEvaluationReportByPeriodId(Long periodId, String empNo) {
        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        return TeamEvaluationReportResponse.from(findTeamEvaluationByEmployeeAndPeriodId(employee, periodId));
    }

    public TeamEvaluation findTeamEvaluationByEmployeeAndPeriodId(Employee employee, Long periodId) {
        return teamEvaluationRepository.findByTeamAndPeriodId(employee.getTeam(), periodId)
                .orElseThrow(() -> new CustomException(TEAM_EVALUATION_DOES_NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public List<FinalEvaluationAchievementStatsResponse> getFinalTeamAndAllAverageAchievementRate(String empNo) {
        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        return teamEvaluationRepository.findTeamAndAllAverageByYear(employee.getTeam().getId()).stream()
                .map(FinalEvaluationAchievementStatsResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeamEvaluationStatusResponse getTeamEvaluationStatus(String empNo) {
        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        LocalDate today = LocalDate.now();

        return teamEvaluationRepository.findByTeamAndPeriod_StartDateLessThanEqualAndPeriod_EndDateGreaterThanEqual(employee.getTeam(), today, today)
                .map(TeamEvaluationStatusResponse::from)
                .orElseThrow(() -> new CustomException(TEAM_EVALUATION_DOES_NOT_EXIST));
    }
}
