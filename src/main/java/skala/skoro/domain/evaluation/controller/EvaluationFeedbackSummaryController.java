package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.evaluation.dto.EvaluationFeedbackSummaryResponse;
import skala.skoro.domain.evaluation.entity.EvaluationFeedbackSummary;
import skala.skoro.domain.evaluation.service.EvaluationFeedbackSummaryService;

@RequestMapping("/evaluation-feedback-summary")
@RequiredArgsConstructor
@RestController
public class EvaluationFeedbackSummaryController {

    private final EvaluationFeedbackSummaryService evaluationFeedbackSummaryService;

    @GetMapping("/{periodId}")
    public ResponseEntity<EvaluationFeedbackSummaryResponse> getEvaluationFeedbackSummary(@PathVariable("periodId") Long periodId) {
        return ResponseEntity.ok(evaluationFeedbackSummaryService.getEvaluationFeedbackSummaryByPeriodId(periodId));
    }
}
