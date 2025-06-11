package skala.skoro.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.repository.EmployeeRepository;
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
}
