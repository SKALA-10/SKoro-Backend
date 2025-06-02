package skala.skoro.domain.evaluation.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;

@Entity
@Table(name = "evaluation_feedback_summaries")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationFeedbackSummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_evaluation_id")
    private Long teamEvaluationId; // PK, FK

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_evaluation_id", insertable = false, updatable = false)
    private TeamEvaluation teamEvaluation;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id")
    private Period period;

    @Builder
    public EvaluationFeedbackSummary(TeamEvaluation teamEvaluation, String content, Period period) {
        this.teamEvaluation = teamEvaluation;
        this.content = content;
        this.period = period;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
