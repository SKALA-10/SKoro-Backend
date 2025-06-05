package skala.skoro.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "현재 로그인한 유저가 없습니다."),
    PERIOD_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "요청한 periodId에 해당하는 기간이 존재하지 않습니다."),
    TEAM_EVALUATION_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "요청한 teamId 또는 periodId에 해당하는 팀 평가가 존재하지 않습니다."),
    INVALID_FINAL_EVALUATION_REQUEST(HttpStatus.BAD_REQUEST, "요청한 평가가 최종 평가가 아닙니다."),
    INVALID_NON_FINAL_EVALUATION_REQUEST(HttpStatus.BAD_REQUEST, "요청한 평가가 비최종 평가가 아닙니다."),
    EVALUATION_FEEDBACK_SUMMARY_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "요청에 해당하는 평가 피드백 요약이 존재하지 않습니다."),
    FINAL_EVALUATION_REPORT_DOES_NOT_EXIST(HttpStatus.NOT_FOUND,"요청에 해당하는 최종 평가 레포트가 존재하지 않습니다."),
    FEEDBACK_REPORT_DOES_NOT_EXIST(HttpStatus.NOT_FOUND,"요청에 해당하는 분기 피드백 레포트가 존재하지 않습니다."),
    TEMP_EVALUATION_NOT_EXISTS(HttpStatus.NOT_FOUND, "요청에 해당하는 임시 평가 데이터가 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
