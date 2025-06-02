package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.TaskSummary;

public interface TaskSummaryRepository extends JpaRepository<TaskSummary, Long> {
}
