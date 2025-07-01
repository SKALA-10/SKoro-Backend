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

    @Column(name = "ai_annual_achievement_rate")
    private Integer aiAnnualAchievementRate;

    @Column(name = "ai_annual_performance_summary_comment", columnDefinition = "TEXT")
    private String aiAnnualPerformanceSummaryComment;

    @Column(name = "ai_peer_talk_summary", columnDefinition = "TEXT")
    private String aiPeerTalkSummary;

    @Column(name = "ai_4p_evaluation", columnDefinition = "TEXT")
    private String ai4pEvaluation;

    @Column(name = "cl_reason", columnDefinition = "TEXT")
    private String clReason;

    @Column(name = "ai_growth_coaching", columnDefinition = "TEXT")
    private String aiGrowthCoaching;

    @Column(name = "overall_comment", columnDefinition = "TEXT")
    private String overallComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_evaluation_id")
    private TeamEvaluation teamEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    public static FinalEvaluationReport of(TeamEvaluation teamEvaluation, Employee employee) {
        return FinalEvaluationReport.builder()
                .teamEvaluation(teamEvaluation)
                .employee(employee)
                .build();
    }
}
