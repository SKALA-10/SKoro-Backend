package skala.skoro.domain.evaluation.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;

@Entity
@Table(name = "manager_evaluations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerEvaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_evaluation_id")
    private Long id;

    @Column(name = "report", columnDefinition = "TEXT")
    private String report;

    // 평가자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    // 평가대상
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_emp_no")
    private Employee targetEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_evaluation_id")
    private TeamEvaluation teamEvaluation;


    @Builder
    public ManagerEvaluation(String report, Employee employee, Employee targetEmployee, TeamEvaluation teamEvaluation) {
        this.report = report;
        this.employee = employee;
        this.targetEmployee = targetEmployee;
        this.teamEvaluation = teamEvaluation;
    }

    public void updateReport(String report) {
        this.report = report;
    }
}
