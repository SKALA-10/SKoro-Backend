package skala.skoro.domain.evaluation.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.employee.entity.Team;
import skala.skoro.domain.period.entity.Period;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "team_evaluations")
public class TeamEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String report;

    private Status status;

    private Integer averageAchievementRate;

    private Integer relativePerformance;

    private Integer yearOverYearGrowth;

    @Column(columnDefinition = "TEXT")
    private String teamPerformanceSummary;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Period period;
}
