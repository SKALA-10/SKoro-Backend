package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.auth.dto.CustomUserDetails;
import skala.skoro.domain.evaluation.dto.FinalEvaluationAchievementStatsResponse;
import skala.skoro.domain.evaluation.dto.TeamEvaluationDetailResponse;
import skala.skoro.domain.evaluation.dto.TeamEvaluationReportResponse;
import skala.skoro.domain.evaluation.dto.TeamEvaluationStatusResponse;
import skala.skoro.domain.evaluation.service.TeamEvaluationService;
import java.util.List;

@RestController
@RequestMapping("/team-evaluation")
@RequiredArgsConstructor
public class TeamEvaluationController {

    private final TeamEvaluationService teamEvaluationService;

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<TeamEvaluationDetailResponse>> findTeamEvaluationsByYear(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(teamEvaluationService.findTeamEvaluationsByYear(user.getUsername()));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/report/{periodId}")
    public ResponseEntity<TeamEvaluationReportResponse> getTeamEvaluationReport(@PathVariable Long periodId, @AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(teamEvaluationService.getTeamEvaluationReportByPeriodId(periodId, user.getUsername()));
    }

    // 최종 평가인 팀 평가의 평균 달성률, 본부(팀 전체)의 평균 달성률 조회
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/average-achievement-rate")
    public ResponseEntity<List<FinalEvaluationAchievementStatsResponse>> getFinalTeamEvaluationAverageAchievementRate(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(teamEvaluationService.getFinalTeamAndAllAverageAchievementRate(user.getUsername()));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/status")
    public ResponseEntity<TeamEvaluationStatusResponse> getTeamEvaluationStatus(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(teamEvaluationService.getTeamEvaluationStatus(user.getUsername()));
    }
}
