package skala.skoro.domain.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.ai.dto.PromptRequest;
import skala.skoro.domain.ai.entity.Prompt;
import skala.skoro.domain.ai.repository.PromptRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;

    public void upsertPrompt(PromptRequest request) {
        promptRepository.findFirstBy()
                .ifPresentOrElse(
                        p -> p.updatePrompt(request.getPrompt()),
                        () -> promptRepository.save(Prompt.from(request))
                );
    }
}
