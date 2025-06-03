package skala.skoro.domain.evaluation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skala.skoro.domain.evaluation.entity.FeedbackReport;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FeedbackReportResponse {
    private Long feedbackReportId;
    private String report;

    public static FeedbackReportResponse from(FeedbackReport feedbackReport) {
        return new FeedbackReportResponse(feedbackReport.getId(), feedbackReport.getReport());
    }
}
