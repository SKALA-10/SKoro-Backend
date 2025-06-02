package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skala.skoro.domain.evaluation.dto.PeerEvaluationDetailResponse;
import skala.skoro.domain.evaluation.dto.SubmitPeerEvaluationRequest;
import skala.skoro.domain.evaluation.service.PeerEvaluationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/peer-evaluation")
public class PeerEvaluationController {

    private final PeerEvaluationService peerEvaluationService;

    @GetMapping("/{peerEvaluationId}")
    public PeerEvaluationDetailResponse getDetail(@PathVariable Long peerEvaluationId) {
        return peerEvaluationService.getPeerEvaluationDetail(peerEvaluationId);
    }

    @PostMapping("/{peerEvaluationId}/submit")
    public ResponseEntity<Void> submitEvaluation(
            @PathVariable Long peerEvaluationId,
            @RequestBody SubmitPeerEvaluationRequest request) {
        peerEvaluationService.submitPeerEvaluation(peerEvaluationId, request);
        return ResponseEntity.ok().build();
    }
}
