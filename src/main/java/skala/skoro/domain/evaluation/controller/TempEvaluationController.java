package skala.skoro.domain.evaluation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skala.skoro.domain.evaluation.dto.TempEvaluationRequest;
import skala.skoro.domain.evaluation.dto.TempEvaluationResponse;
import skala.skoro.domain.evaluation.service.TempEvaluationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/temp-evaluations")
public class TempEvaluationController {

    private final TempEvaluationService tempEvaluationService;

    @GetMapping
    public ResponseEntity<List<TempEvaluationResponse>> getTeamTempEvaluations() {
        return ResponseEntity.ok(tempEvaluationService.getTeamTempEvaluations());
    }

    @PutMapping("/{empNo}")
    public ResponseEntity<Void> updateTeamMemberTempEvaluations(@PathVariable String empNo, @RequestBody TempEvaluationRequest request) {
        tempEvaluationService.updateTeamMemberTempEvaluations(empNo, request);
        return ResponseEntity.ok().build();
    }
}
