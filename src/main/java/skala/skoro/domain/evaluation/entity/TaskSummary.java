package skala.skoro.domain.evaluation.entity;


import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;

@Entity
@Table(name = "task_summaries")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskSummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_summary_id")
    private Long id;

    @Column(name = "task_summary", columnDefinition = "TEXT")
    private String taskSummary;

    @Column(name = "task_id")
    private Long taskId; // Task 테이블이 별도로 있다면 @ManyToOne으로 변경 가능

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id")
    private Period period;

    @Builder
    public TaskSummary(String taskSummary, Long taskId, Period period) {
        this.taskSummary = taskSummary;
        this.taskId = taskId;
        this.period = period;
    }

    public void updateTaskSummary(String taskSummary) {
        this.taskSummary = taskSummary;
    }
}
