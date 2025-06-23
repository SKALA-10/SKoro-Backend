package skala.skoro.domain.employee.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmployeeFinalEvaluationResponse {
    private String empNo;
    private String empName;
    private String profileImage;
    private String position;
    private Integer contributionRate;
    private Integer aiAnnualAchievementRate;
    private Double score;
    private Integer ranking;

    public static EmployeeFinalEvaluationResponse from(RankedFinalEvaluationProjection projection) {
        return EmployeeFinalEvaluationResponse.builder()
                .empNo(projection.getEmpNo())
                .empName(projection.getEmpName())
                .profileImage(projection.getProfileImage())
                .position(projection.getPosition())
                .contributionRate(projection.getContributionRate())
                .aiAnnualAchievementRate(projection.getAiAnnualAchievementRate())
                .score(projection.getScore())
                .ranking(projection.getRanking())
                .build();
    }
}
