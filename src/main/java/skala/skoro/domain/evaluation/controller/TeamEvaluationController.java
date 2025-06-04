package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.evaluation.dto.FinalEvaluationAchievementStatsResponse;
import skala.skoro.domain.evaluation.dto.TeamEvaluationReportResponse;
import skala.skoro.domain.evaluation.service.TeamEvaluationService;

import java.util.List;

@RequestMapping("/team-evaluation")
@RequiredArgsConstructor
@RestController
public class TeamEvaluationController {

    private final TeamEvaluationService teamEvaluationService;

    @GetMapping("/report/{periodId}")
    public ResponseEntity<TeamEvaluationReportResponse> getTeamEvaluationReport(@PathVariable Long periodId){
        return ResponseEntity.ok(teamEvaluationService.getTeamEvaluationReportByPeriodId(periodId));
    }

    // 최종 평가인 팀 평가의 평균 달성률, 본부(팀 전체)의 평균 달성률 조회
    @GetMapping("/average-achievement-rate")
    public ResponseEntity<List<FinalEvaluationAchievementStatsResponse>> getFinalTeamEvaluationAverageAchievementRate(){
        return ResponseEntity.ok(teamEvaluationService.getFinalTeamAndAllAverageAchievementRate());
    }
}
