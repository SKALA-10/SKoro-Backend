package skala.skoro.domain.period.dto;

import lombok.*;
import skala.skoro.domain.period.entity.Period;
import skala.skoro.domain.period.entity.Unit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TeamEvaluationPeriodResponse {
    private Long periodId;
    private Integer year;
    private String periodName;
    private boolean isFinal;
    private Unit unit;
    private Integer orderInYear;

    public static TeamEvaluationPeriodResponse from(Period period) {
        return TeamEvaluationPeriodResponse.builder()
                .periodId(period.getId())
                .year(period.getYear())
                .periodName(period.getPeriodName())
                .isFinal(period.getIsFinal())
                .unit(period.getUnit())
                .orderInYear(period.getOrderInYear())
                .build();
    }
}
