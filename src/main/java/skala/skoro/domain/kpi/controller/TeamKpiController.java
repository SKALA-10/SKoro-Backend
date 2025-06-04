package skala.skoro.domain.kpi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.kpi.dto.TeamKpiDetailResponse;
import skala.skoro.domain.kpi.service.TeamKpiService;
import java.util.List;

@RequestMapping("/team-kpis")
@RequiredArgsConstructor
@RestController
public class TeamKpiController {

    private final TeamKpiService teamKpiService;

    @GetMapping
    public ResponseEntity<List<TeamKpiDetailResponse>> getTeamKpis() {
        return ResponseEntity.ok(teamKpiService.getTeamKpis());
    }
}
