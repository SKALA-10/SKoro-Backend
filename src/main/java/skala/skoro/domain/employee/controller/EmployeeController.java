package skala.skoro.domain.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.employee.dto.EmployeeFinalEvaluationResponse;
import skala.skoro.domain.employee.dto.EmployeeNonFinalEvaluationResponse;
import skala.skoro.domain.employee.dto.EmployeeSummaryResponse;
import skala.skoro.domain.employee.service.EmployeeService;
import java.util.List;

@RequestMapping("/employees")
@RequiredArgsConstructor
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeSummaryResponse>> getEmployeesByPeriodAndTeam(){
        return ResponseEntity.ok(employeeService.getEmployeesByTeam());
    }

    @GetMapping("/{periodId}/final")
    public ResponseEntity<List<EmployeeFinalEvaluationResponse>> getFinalEmployeeSummary(@PathVariable Long periodId){
        return ResponseEntity.ok(employeeService.getFinalEmployeeEvaluationsByPeriod(periodId));
    }

    @GetMapping("/{periodId}/non-final")
    public ResponseEntity<List<EmployeeNonFinalEvaluationResponse>> getNonFinalEmployeeSummary(@PathVariable Long periodId){
        return ResponseEntity.ok(employeeService.getNonFinalEmployeeEvaluationsByPeriod(periodId));
    }



}
