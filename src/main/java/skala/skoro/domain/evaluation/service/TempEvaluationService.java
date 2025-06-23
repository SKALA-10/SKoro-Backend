package skala.skoro.domain.evaluation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.service.EmployeeService;
import skala.skoro.domain.evaluation.dto.TempEvaluationRequest;
import skala.skoro.domain.evaluation.dto.TempEvaluationResponse;
import skala.skoro.domain.evaluation.entity.TempEvaluation;
import skala.skoro.domain.evaluation.repository.TempEvaluationRepository;
import skala.skoro.global.exception.CustomException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Optional;

import static skala.skoro.global.exception.ErrorCode.TEMP_EVALUATION_NOT_EXISTS;

@Service
@Transactional
@RequiredArgsConstructor
public class TempEvaluationService {

    private final EmployeeService employeeService;

    private final TempEvaluationRepository tempEvaluationRepository;

    @Transactional(readOnly = true)
    public List<TempEvaluationResponse> getTeamTempEvaluations(String empNo) {
        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        return employeeService.findByTeam(employee.getTeam()).stream()
                .map(tempEvaluationRepository::findByEmployee)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(TempEvaluationResponse::from)
                .collect(Collectors.toList());
    }

    public void updateTeamMemberTempEvaluations(String empNo, TempEvaluationRequest request) {
        TempEvaluation previous = tempEvaluationRepository.findByEmployee_EmpNo(empNo)
                .orElseThrow(() -> new CustomException(TEMP_EVALUATION_NOT_EXISTS));

        previous.updateTempEvaluation(request);
    }
}
