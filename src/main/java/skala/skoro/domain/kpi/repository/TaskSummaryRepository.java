package skala.skoro.domain.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.kpi.entity.TaskSummary;

public interface TaskSummaryRepository extends JpaRepository<TaskSummary, Long> {
}
