package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.evaluation.dto.TeamEvaluationReportResponse;
import skala.skoro.domain.evaluation.service.TeamEvaluationService;

@RequestMapping("/team-evaluation")
@RequiredArgsConstructor
@RestController
public class TeamEvaluationController {

    private final TeamEvaluationService teamEvaluationService;

    @GetMapping("/report/{periodId}")
    public ResponseEntity<TeamEvaluationReportResponse> getTeamEvaluationReport(@PathVariable Long periodId){
        return ResponseEntity.ok(teamEvaluationService.getTeamEvaluationReportByPeriodId(periodId));
    }
}
