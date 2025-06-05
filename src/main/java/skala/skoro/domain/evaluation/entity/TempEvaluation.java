package skala.skoro.domain.evaluation.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import skala.skoro.domain.evaluation.dto.TempEvaluationRequest;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash("TempEvaluation") // Redis key TempEvaluation:{empNo}
public class TempEvaluation implements Serializable {
    @Id
    private String empNo;

    private String aiReason;

    private Double score;

    private String comment;

    private String reason;

    @Builder.Default
    private Status status = Status.NOT_STARTED;

    public static TempEvaluation of(String empNo, TempEvaluationRequest request, TempEvaluation previousTempEvaluation){
        return TempEvaluation.builder()
                .empNo(empNo)
                .aiReason(previousTempEvaluation.getAiReason())
                .score(request.getScore())
                .comment(request.getComment())
                .reason(request.getReason())
                .status(Status.COMPLETED)
                .build();
    }
}