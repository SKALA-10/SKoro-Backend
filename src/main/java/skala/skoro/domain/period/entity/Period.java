package skala.skoro.domain.period.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.period.dto.PeriodCreateAndUpdateRequest;
import skala.skoro.domain.common.BaseEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "periods")
public class Period extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id")
    private Long id;

    private Integer year;

    private String periodName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Unit unit = Unit.QUARTER;

    private Boolean isFinal;

    private Integer orderInYear;

    private LocalDate startDate;

    private LocalDate endDate;

    public void updatePeriod(PeriodCreateAndUpdateRequest request) {
        this.year = request.getStartDate().getYear();
        this.periodName = request.getPeriodName();
        this.unit = request.getUnit();
        this.isFinal = request.getIsFinal();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }

    public static Period of(PeriodCreateAndUpdateRequest request, Integer orderInYear) {
        return Period.builder()
                .year(request.getStartDate().getYear())
                .periodName(request.getPeriodName())
                .unit(request.getUnit())
                .isFinal(request.getIsFinal())
                .orderInYear(orderInYear)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }
}
