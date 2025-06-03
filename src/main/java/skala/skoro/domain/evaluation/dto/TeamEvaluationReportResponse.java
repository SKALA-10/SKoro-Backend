package skala.skoro.domain.evaluation.dto;

import lombok.*;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeamEvaluationReportResponse {
    private Long teamEvaluationId;
    private String report;

    public static TeamEvaluationReportResponse from(TeamEvaluation teamEvaluation) {
        return new TeamEvaluationReportResponse(teamEvaluation.getId(), teamEvaluation.getReport());
    }
}
