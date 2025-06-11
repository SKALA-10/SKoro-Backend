package skala.skoro.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skala.skoro.domain.admin.service.PeerEvaluationNotificationService;
import skala.skoro.domain.period.dto.PeriodAvailableResponse;
import skala.skoro.domain.period.dto.PeriodCreateAndUpdateRequest;
import skala.skoro.domain.period.service.PeriodService;
import java.util.List;

@Tag(name = "관리자 기능")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PeriodService periodService;
    private final PeerEvaluationNotificationService peerEvaluationNotificationService;

    @Operation(summary = "[관리자] 평가 기간 생성")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/period")
    public ResponseEntity<?> createPeriod(@RequestBody PeriodCreateAndUpdateRequest request) {
        periodService.createPeriod(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "[관리자] 현재 진행 중인 혹은 다가올 평가 기간이 있으면 조회")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/period/available")
    public ResponseEntity<List<PeriodAvailableResponse>> findPeriodsAvailable() {
        return ResponseEntity.ok(periodService.findPeriodAvailable());
    }

    @Operation(summary = "[관리자] 평가 기간 수정")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/period/{periodId}")
    public ResponseEntity<?> updatePeriod(@PathVariable Long periodId,
                                          @RequestBody PeriodCreateAndUpdateRequest request) {
        periodService.updatePeriod(periodId, request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[관리자] 동료 평가 시작 메일 발송")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/notify/peer-evaluation")
    public ResponseEntity<Void> notifyPeerEvaluation(Long periodId) {
        peerEvaluationNotificationService.sendPeerEvaluationNotification(periodId);
        return ResponseEntity.ok().build();
    }
}
