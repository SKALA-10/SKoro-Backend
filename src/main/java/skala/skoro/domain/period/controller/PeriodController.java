package skala.skoro.domain.period.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.period.service.PeriodService;

@RequiredArgsConstructor
@RestController
public class PeriodController {

    private final PeriodService periodService;

    @GetMapping("/periods")
    public ResponseEntity<?> getTeamEvaluationPeriods() {
        return ResponseEntity.ok(periodService.getTeamEvaluationPeriods());
    }
}
