package skala.skoro.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skala.skoro.domain.period.dto.PeriodAvailableResponse;
import skala.skoro.domain.period.dto.PeriodCreateAndUpdateRequest;
import skala.skoro.domain.period.service.PeriodService;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PeriodService periodService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/period")
    public ResponseEntity<?> createPeriod(@RequestBody PeriodCreateAndUpdateRequest request) {
        periodService.createPeriod(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/period/available")
    public ResponseEntity<List<PeriodAvailableResponse>> findPeriodsAvailable() {
        return ResponseEntity.ok(periodService.findPeriodAvailable());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/period/{periodId}")
    public ResponseEntity<?> updatePeriod(@PathVariable Long periodId,
                                          @RequestBody PeriodCreateAndUpdateRequest request) {
        periodService.updatePeriod(periodId, request);

        return ResponseEntity.ok().build();
    }
}
