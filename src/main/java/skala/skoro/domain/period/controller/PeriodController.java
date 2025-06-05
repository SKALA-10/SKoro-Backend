package skala.skoro.domain.period.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.auth.dto.CustomUserDetails;
import skala.skoro.domain.period.dto.TeamEvaluationPeriodResponse;
import skala.skoro.domain.period.service.PeriodService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeriodController {

    private final PeriodService periodService;

    @GetMapping("/periods")
    public ResponseEntity<List<TeamEvaluationPeriodResponse>> getTeamEvaluationPeriods(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(periodService.getTeamEvaluationPeriods(user.getUsername()));
    }
}
