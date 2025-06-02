package skala.skoro.domain.kpi.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;
import skala.skoro.domain.employee.entity.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String taskName;

    @Lob
    private String targetLevel;

    private Integer progress;

    private Integer achievementRate;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeamKpi teamKpi;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
}
