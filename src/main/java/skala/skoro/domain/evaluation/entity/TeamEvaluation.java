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
    @Column(name = "team_evaluation_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String report;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer averageAchievementRate;

    private Integer relativePerformance;

    private Integer yearOverYearGrowth;

    @Column(columnDefinition = "TEXT")
    private String teamPerformanceSummary;

    @Column(name = "ai_team_overall_analysis_comment", columnDefinition = "TEXT")
    private String aiTeamOverallAnalysisComment;

    @Column(name = "ai_collaboration_matrix", columnDefinition = "TEXT")
    private String aiCollaborationMatrix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id")
    private Period period;
}
