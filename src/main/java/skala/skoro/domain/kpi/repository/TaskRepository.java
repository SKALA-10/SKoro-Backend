package skala.skoro.domain.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.kpi.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
