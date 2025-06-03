package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.evaluation.dto.FinalEvaluationReportResponse;
import skala.skoro.domain.evaluation.service.FinalEvaluationReportService;

@RequiredArgsConstructor
@RestController
public class FinalEvaluationReportController {

    private final FinalEvaluationReportService finalEvaluationReportService;

    @GetMapping("/employees/{empNo}/final-evaluation-report/{periodId}")
    public ResponseEntity<FinalEvaluationReportResponse> getFinalEvaluationReport(
            @PathVariable String empNo,
            @PathVariable Long periodId) {
        return ResponseEntity.ok(finalEvaluationReportService.getFinalEvaluationReport(empNo, periodId));
    }
}
