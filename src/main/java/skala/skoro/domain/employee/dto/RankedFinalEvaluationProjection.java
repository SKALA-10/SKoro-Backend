package skala.skoro.domain.employee.dto;

public interface RankedFinalEvaluationProjection {
    String getEmpNo();
    String getEmpName();
    String getProfileImage();
    String getPosition();
    Integer getContributionRate();
    Integer getAiAnnualAchievementRate();
    Double getScore();
    Integer getRanking();
}
