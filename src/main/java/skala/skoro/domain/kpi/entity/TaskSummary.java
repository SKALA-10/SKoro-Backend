package skala.skoro.domain.kpi.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;
import skala.skoro.domain.period.entity.Period;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_summaries")
public class TaskSummary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    private Period period;
}
