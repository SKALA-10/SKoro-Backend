package skala.skoro.domain.evaluation.dto;

import lombok.*;
import skala.skoro.domain.evaluation.entity.Status;
import skala.skoro.domain.evaluation.entity.TeamEvaluation;
import skala.skoro.domain.period.entity.Period;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TeamEvaluationStatusResponse {
    private Long teamEvaluationId;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer year;
    private String periodName;
    private Boolean isFinal;

    public static TeamEvaluationStatusResponse from(TeamEvaluation teamEvaluation) {
        Period period = teamEvaluation.getPeriod();
        return TeamEvaluationStatusResponse.builder()
                .teamEvaluationId(teamEvaluation.getId())
                .status(teamEvaluation.getStatus())
                .startDate(period.getStartDate())
                .endDate(period.getEndDate())
                .year(period.getYear())
                .periodName(period.getPeriodName())
                .isFinal(period.getIsFinal())
                .build();
    }
}
