package skala.skoro.domain.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.auth.dto.CustomUserDetails;
import skala.skoro.domain.employee.dto.EmployeeFinalEvaluationResponse;
import skala.skoro.domain.employee.dto.EmployeeNonFinalEvaluationResponse;
import skala.skoro.domain.employee.dto.EmployeeSummaryResponse;
import skala.skoro.domain.employee.service.EmployeeService;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeSummaryResponse>> getEmployeesByPeriodAndTeam(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(employeeService.getEmployeesByTeam(user.getUsername()));
    }

    @GetMapping("/{periodId}/final")
    public ResponseEntity<List<EmployeeFinalEvaluationResponse>> getFinalEmployeeSummary(@PathVariable Long periodId, @AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(employeeService.getFinalEmployeeEvaluationsByPeriod(periodId, user.getUsername()));
    }

    @GetMapping("/{periodId}/non-final")
    public ResponseEntity<List<EmployeeNonFinalEvaluationResponse>> getNonFinalEmployeeSummary(@PathVariable Long periodId, @AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(employeeService.getNonFinalEmployeeEvaluationsByPeriod(periodId, user.getUsername()));
    }



}
