package skala.skoro.domain.evaluation.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;
import skala.skoro.domain.employee.entity.Employee;

@Entity
@Table(name = "feedback_reports")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_report_id")
    private Long id;

    @Column(name = "report", columnDefinition = "TEXT")
    private String report;

    private Integer ranking;

    @Column(name = "contribution_rate")
    private Integer contributionRate;

    private String skill;     // 자유입력

    private String attitude;  // 자유입력

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_evaluation_id")
    private TeamEvaluation teamEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Builder
    public FeedbackReport(String report, Integer ranking, Integer contributionRate, String skill, String attitude, TeamEvaluation teamEvaluation, Employee employee) {
        this.report = report;
        this.ranking = ranking;
        this.contributionRate = contributionRate;
        this.skill = skill;
        this.attitude = attitude;
        this.teamEvaluation = teamEvaluation;
        this.employee = employee;
    }

    public void update(String report, Integer ranking, Integer contributionRate, String skill, String attitude) {
        this.report = report;
        this.ranking = ranking;
        this.contributionRate = contributionRate;
        this.skill = skill;
        this.attitude = attitude;
    }
}
