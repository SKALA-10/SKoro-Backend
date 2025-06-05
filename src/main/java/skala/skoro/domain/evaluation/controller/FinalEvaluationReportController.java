package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.auth.dto.CustomUserDetails;
import skala.skoro.domain.evaluation.dto.FinalEvaluationReportResponse;
import skala.skoro.domain.evaluation.service.FinalEvaluationReportService;

@RestController
@RequiredArgsConstructor
public class FinalEvaluationReportController {

    private final FinalEvaluationReportService finalEvaluationReportService;

    @GetMapping("/employees/{empNo}/final-evaluation-report/{periodId}")
    public ResponseEntity<FinalEvaluationReportResponse> getTeamMemberFinalEvaluationReport(
            @PathVariable String empNo,
            @PathVariable Long periodId) {
        return ResponseEntity.ok(finalEvaluationReportService.getTeamMemberFinalEvaluationReport(empNo, periodId));
    }

    @GetMapping("/final-evaluation-report/{periodId}")
    public ResponseEntity<FinalEvaluationReportResponse> getFinalEvaluationReport(@PathVariable Long periodId, @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(finalEvaluationReportService.getFinalEvaluationReport(periodId, user.getUsername()));
    }
}
