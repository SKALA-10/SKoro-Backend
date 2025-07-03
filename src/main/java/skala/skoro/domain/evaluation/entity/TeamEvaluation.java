package skala.skoro.domain.evaluation.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;
import skala.skoro.domain.employee.entity.Team;
import skala.skoro.domain.period.entity.Period;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "team_evaluations")
public class TeamEvaluation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_evaluation_id")
    private Long id;

    @Column(columnDefinition = "JSON")
    private String report;

    @Enumerated(EnumType.STRING)
    private TeamEvaluationStatus status;

    private Integer averageAchievementRate;

    private String relativePerformance;

    private Integer yearOverYearGrowth;

    @Column(name = "ai_team_overall_analysis_comment", columnDefinition = "TEXT")
    private String aiTeamOverallAnalysisComment;

    @Column(name = "ai_collaboration_matrix", columnDefinition = "JSON")
    private String aiCollaborationMatrix;

    @Column(name = "ai_team_comparison", columnDefinition = "JSON")
    private String aiTeamComparison;

    @Column(name = "ai_team_coaching", columnDefinition = "JSON")
    private String aiTeamCoaching;

    @Column(name = "overall_comment", columnDefinition = "TEXT")
    private String overallComment;

    @Column(name = "ai_risk", columnDefinition = "JSON")
    private String aiRisk;

    @Column(name = "ai_plan", columnDefinition = "JSON")
    private String aiPlan;

    @Column(name = "middle_report", columnDefinition = "JSON")
    private String middleReport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id")
    private Period period;

    public static TeamEvaluation of(Team team, Period period, TeamEvaluationStatus status) {
        return TeamEvaluation.builder()
                .team(team)
                .period(period)
                .status(status)
                .build();
    }

    public void updateStatus(TeamEvaluationStatus status) {
        this.status = status;
    }
}
