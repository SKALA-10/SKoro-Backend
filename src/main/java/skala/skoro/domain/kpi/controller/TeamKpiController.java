package skala.skoro.domain.kpi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.auth.dto.CustomUserDetails;
import skala.skoro.domain.kpi.dto.TeamKpiDetailResponse;
import skala.skoro.domain.kpi.service.TeamKpiService;
import java.util.List;

@RestController
@RequestMapping("/team-kpis")
@RequiredArgsConstructor
public class TeamKpiController {

    private final TeamKpiService teamKpiService;

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<TeamKpiDetailResponse>> getTeamKpis(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(teamKpiService.getTeamKpis(user.getUsername()));
    }
}
