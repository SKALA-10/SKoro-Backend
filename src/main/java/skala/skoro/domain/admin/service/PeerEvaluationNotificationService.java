package skala.skoro.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.repository.EmployeeRepository;
import skala.skoro.domain.evaluation.entity.PeerEvaluation;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import skala.skoro.domain.evaluation.repository.PeerEvaluationRepository;
import skala.skoro.domain.evaluation.repository.TeamEvaluationRepository;
import skala.skoro.domain.kpi.entity.TeamKpi;
import skala.skoro.domain.kpi.repository.TaskRepository;
import skala.skoro.domain.kpi.repository.TeamKpiRepository;
import skala.skoro.domain.period.entity.Period;
import skala.skoro.domain.period.repository.PeriodRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeerEvaluationNotificationService {

    private final EmailService emailService;
    private final PeriodRepository periodRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamEvaluationRepository teamEvaluationRepository;
    private final TeamKpiRepository teamKpiRepository;
    private final TaskRepository taskRepository;
    private final PeerEvaluationRepository peerEvaluationRepository;

    public void sendPeerEvaluationNotification(Long periodId) {
        Period period = periodRepository.findById(periodId)
                .orElseThrow(() -> new IllegalArgumentException("평가 기간이 존재하지 않습니다."));

        LocalDate endDate = period.getEndDate();
        LocalDate startDate = endDate.minusDays(7);
        LocalDate evaluationEndDate = startDate.plusDays(4);

        String subject = "[요청] %d년 %s PeerTalk 요청이 도착했어요!"
                .formatted(period.getYear(), period.getPeriodName());

        String html = """
        <!DOCTYPE html>
        <html lang="ko">
        <head>
          <meta charset="UTF-8">
          <style>
            body { font-family: '맑은 고딕', Arial, sans-serif; color: #222; }
            .mail-container { padding: 32px 24px; border-radius: 12px; background: #f6f8fb; }
            .mail-title { font-size: 1.3em; font-weight: bold; color: #2F80ED; margin-bottom: 16px; }
            .mail-body { font-size: 1.08em; line-height: 1.7; }
            .period { color: #3870b7; font-weight: bold; }
            .sign { margin-top: 30px; font-weight: 500; color: #444; }
          </style>
        </head>
        <body>
          <div class="mail-container">
            <div class="mail-title">안녕하세요. SK AX 인사팀입니다.</div>
            <div class="mail-body">
              %s 동안 함께 업무를 수행한 동료를 위한 <b>PeerTalk</b>를 진행합니다.<br>
              협업과 몰입을 위한 대화에 꼭 참여해 주세요.<br>
              <br>
              <span class="period">PeerTalk 시행 기간 : %s ~ %s</span>
            </div>
            <div class="sign">감사합니다.<br>SKoro 운영팀 드림</div>
          </div>
        </body>
        </html>
        """.formatted(period.getPeriodName(), startDate, evaluationEndDate);

        // **전체 사원 이메일 목록 조회**
        List<Employee> allEmployees = employeeRepository.findAll();

        for (Employee employee : allEmployees) {
            String to = employee.getEmail();
            if (to != null && !to.isBlank()) {
                emailService.sendMail(to, subject, html);
            }
        }
    }

    public void generatePeerEvaluations(Long teamEvaluationId) {
        TeamEvaluation teamEval = teamEvaluationRepository.findById(teamEvaluationId)
                .orElseThrow(() -> new IllegalArgumentException("팀 평가 없음"));

        Long teamId = teamEval.getTeam().getId();

        // 2. 해당 팀의 KPI 전체 조회
        List<TeamKpi> teamKpis = teamKpiRepository.findByTeam_Id(teamId);

        for (TeamKpi teamKpi : teamKpis) {
            Long teamKpiId = teamKpi.getId();
            String kpiName = teamKpi.getKpiName();

            // 3. 해당 KPI를 수행한 사원들 조회
            List<String> empNos = taskRepository.findEmpNosByTeamKpiId(teamKpiId);

            // 4. 사원-사원(자기 자신 제외) 모든 조합으로 동료 평가 레코드 생성
            for (String evaluator : empNos) {
                for (String target : empNos) {
                    if (!evaluator.equals(target)) {
                        Employee evaluatorEmp = employeeRepository.findById(evaluator)
                                .orElseThrow(() -> new IllegalArgumentException("사번 없음: " + evaluator));
                        Employee targetEmp = employeeRepository.findById(target)
                                .orElseThrow(() -> new IllegalArgumentException("사번 없음: " + target));

                        PeerEvaluation entity = PeerEvaluation.builder()
                                .isCompleted(false)
                                .jointTask(kpiName)
                                .employee(evaluatorEmp)
                                .targetEmployee(targetEmp)
                                .teamEvaluation(teamEval)
                                .build();

                        peerEvaluationRepository.save(entity);
                    }
                }
            }
        }
    }

    // 동료평가 시작: 메일 발송 + 동료평가 매핑 자동 생성
    public void startPeerEvaluation(Long periodId) {
        // 1. 메일 발송
        sendPeerEvaluationNotification(periodId);

        // 2. 팀별 동료평가 자동 생성
        // - 해당 period에 연결된 모든 TeamEvaluation을 찾아서 반복
        List<TeamEvaluation> teamEvals = teamEvaluationRepository.findByPeriod_Id(periodId);
        for (TeamEvaluation teamEval : teamEvals) {
            generatePeerEvaluations(teamEval.getId());
        }
    }
}
