package skala.skoro.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "현재 로그인한 유저가 없습니다."),
    PERIOD_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "요청한 periodId에 해당하는 평가 기간이 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
