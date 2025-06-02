package skala.skoro.domain.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class KeywordResponse {
    private Long keywordId;
    private String keywordName;
}
