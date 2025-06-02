package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.FeedbackReport;

public interface FeedbackReportRepository extends JpaRepository<FeedbackReport, Long> {
}
