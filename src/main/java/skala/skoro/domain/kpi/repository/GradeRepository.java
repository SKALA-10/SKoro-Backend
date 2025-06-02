package skala.skoro.domain.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.kpi.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
