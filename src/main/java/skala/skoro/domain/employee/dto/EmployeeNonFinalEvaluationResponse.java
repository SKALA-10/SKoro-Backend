package skala.skoro.domain.employee.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmployeeNonFinalEvaluationResponse {
    private String empNo;
    private String empName;
    private String profileImage;
    private String position;
    private Integer contributionRate;
    private Integer aiAchievementRate;
    private String attitude;
    private Integer ranking;

    public static EmployeeNonFinalEvaluationResponse from(RankedNonFinalEvaluationProjection projection) {
        return EmployeeNonFinalEvaluationResponse.builder()
                .empNo(projection.getEmpNo())
                .empName(projection.getEmpName())
                .profileImage(projection.getProfileImage())
                .position(projection.getPosition())
                .contributionRate(projection.getContributionRate())
                .aiAchievementRate(projection.getAiAchievementRate())
                .attitude(projection.getAttitude())
                .ranking(projection.getRanking())
                .build();
    }
}
