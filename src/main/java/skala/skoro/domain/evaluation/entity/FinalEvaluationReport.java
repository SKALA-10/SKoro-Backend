package skala.skoro.domain.evaluation.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;
import skala.skoro.domain.employee.entity.Employee;

@Entity
@Table(name = "final_evaluation_reports",
        uniqueConstraints = @UniqueConstraint(columnNames = {"emp_no", "team_evaluation_id"}))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalEvaluationReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "final_evaluation_report_id")
    private Long id;

    @Column(name = "report", columnDefinition = "TEXT")
    private String report;

    private Integer ranking;

    private Double score;

    @Column(name = "contribution_rate")
    private Integer contributionRate;

    private String skill;

    @Column(name = "ai_annual_achievement_rate")
    private Integer aiAnnualAchievementRate;

    @Column(name = "ai_annual_performance_summary_comment", columnDefinition = "TEXT")
    private String aiAnnualPerformanceSummaryComment;

    @Column(name = "ai_peer_talk_summary", columnDefinition = "TEXT")
    private String aiPeerTalkSummary;

    @Column(name = "ai_4p_evaluation", columnDefinition = "TEXT")
    private String ai4pEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_evaluation_id")
    private TeamEvaluation teamEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Builder
    public FinalEvaluationReport(String report, Integer ranking, Double score, Integer contributionRate, String skill, TeamEvaluation teamEvaluation, Employee employee) {
        this.report = report;
        this.ranking = ranking;
        this.score = score;
        this.contributionRate = contributionRate;
        this.skill = skill;
        this.teamEvaluation = teamEvaluation;
        this.employee = employee;
    }

    public void updateReport(String report, Integer ranking, Double score, Integer contributionRate, String skill) {
        this.report = report;
        this.ranking = ranking;
        this.score = score;
        this.contributionRate = contributionRate;
        this.skill = skill;
    }
}
