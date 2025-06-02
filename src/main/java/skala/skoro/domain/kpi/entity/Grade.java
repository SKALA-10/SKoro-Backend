package skala.skoro.domain.kpi.entity;

import jakarta.persistence.*;
import lombok.*;
import skala.skoro.domain.common.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "grades")
public class Grade extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gradeS;

    private String gradeA;

    private String gradeB;

    private String gradeC;

    private String gradeD;

    private String gradeRule;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeamKpi teamKpi;
}

