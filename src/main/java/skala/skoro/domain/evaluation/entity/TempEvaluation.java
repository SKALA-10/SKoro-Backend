package skala.skoro.domain.evaluation.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.evaluation.dto.TempEvaluationRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "temp_evaluations")
public class TempEvaluation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temp_evaluation_id")
    private Long id;

    private String aiReason;

    private Double score;

    private Double rawScore;

    private Double managerScore;

    private String comment;

    private String reason;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.NOT_STARTED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_evaluation_id")
    private TeamEvaluation teamEvaluation;

    public void updateTempEvaluation (TempEvaluationRequest request){
        this.managerScore = request.getScore();
        this.comment = request.getComment();
        this.reason = request.getReason();
        this.status = Status.COMPLETED;
    }
}