package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.evaluation.dto.FeedbackReportResponse;
import skala.skoro.domain.evaluation.service.FeedbackReportService;

@RequiredArgsConstructor
@RestController
public class FeedbackReportController {

    private final FeedbackReportService feedbackReportService;

    @GetMapping("/employees/{empNo}/feedback-report/{periodId}")
    public ResponseEntity<FeedbackReportResponse> getFeedbackReport(
            @PathVariable("empNo") String empNo,
            @PathVariable("periodId") Long periodId) {
        return ResponseEntity.ok(feedbackReportService.getFeedbackReport(empNo, periodId));
    }
}
