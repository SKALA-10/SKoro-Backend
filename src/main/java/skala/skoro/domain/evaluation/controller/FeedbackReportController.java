package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.auth.dto.CustomUserDetails;
import skala.skoro.domain.evaluation.dto.FeedbackReportResponse;
import skala.skoro.domain.evaluation.service.FeedbackReportService;

@RestController
@RequiredArgsConstructor
public class FeedbackReportController {

    private final FeedbackReportService feedbackReportService;

    @GetMapping("/employees/{empNo}/feedback-report/{periodId}")
    public ResponseEntity<FeedbackReportResponse> getTeamMemberFeedbackReport(
            @PathVariable("empNo") String empNo,
            @PathVariable("periodId") Long periodId) {
        return ResponseEntity.ok(feedbackReportService.getTeamMemberFeedbackReport(empNo, periodId));
    }

    @GetMapping("/feedback-report/{periodId}")
    public ResponseEntity<FeedbackReportResponse> getFeedbackReport(@PathVariable("periodId") Long periodId, @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(feedbackReportService.getFeedbackReport(periodId, user.getUsername()));
    }
}
