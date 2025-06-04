package skala.skoro.domain.kpi.dto;

import lombok.*;
import skala.skoro.domain.kpi.entity.TeamKpi;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TeamKpiDetailResponse {
    private Integer year;
    private String kpiName;
    private Integer progress;
    private String description;

    public static TeamKpiDetailResponse from(TeamKpi teamKpi) {
        return TeamKpiDetailResponse.builder()
                .year(teamKpi.getYear())
                .kpiName(teamKpi.getKpiName())
                .progress(teamKpi.getProgress())
                .description(teamKpi.getKpiDescription())
                .build();
    }
}
