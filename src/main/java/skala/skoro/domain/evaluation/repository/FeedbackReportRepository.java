package skala.skoro.domain.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.evaluation.entity.FeedbackReport;

import java.util.Collection;
import java.util.List;

public interface FeedbackReportRepository extends JpaRepository<FeedbackReport, Long> {
    List<FeedbackReport> findByTeamEvaluationId(Long teamEvaluationId);
}
