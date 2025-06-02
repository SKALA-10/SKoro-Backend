package skala.skoro.domain.period.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.period.entity.Period;

public interface PeriodRepository extends JpaRepository<Period, Long> {
}
