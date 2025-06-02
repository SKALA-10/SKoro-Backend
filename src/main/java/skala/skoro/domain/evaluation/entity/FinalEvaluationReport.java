package skala.skoro.domain.evaluation.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;

@Entity
@Table(name = "final_evaluation_reports")
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_evaluation_id")
    private TeamEvaluation teamEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;
}
