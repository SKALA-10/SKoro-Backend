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
import java.util.stream.Collectors;
import java.util.Optional;

import static skala.skoro.global.exception.ErrorCode.TEMP_EVALUATION_NOT_EXISTS;

@Service
@Transactional
@RequiredArgsConstructor
public class TempEvaluationService {

    private final EmployeeService employeeService;

    private final TempEvaluationRepository redisRepository;

    public List<TempEvaluationResponse> getTeamTempEvaluations() {
        String empNo = "E001"; // TODO

        Employee employee = employeeService.findEmployeeByEmpNo(empNo);

        List<Employee> teamMemberList = employeeService.findByTeam(employee.getTeam());

        return teamMemberList.stream()
                .map(Employee::getEmpNo) // empNo 리스트
                .map(redisRepository::findById) // Optional<TempEvaluation>
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(TempEvaluationResponse::from)
                .collect(Collectors.toList());
    }

    public void updateTeamMemberTempEvaluations(String empNo, TempEvaluationRequest request) {
        TempEvaluation previous = redisRepository.findById(empNo)
                .orElseThrow(() -> new CustomException(TEMP_EVALUATION_NOT_EXISTS));

        redisRepository.save(TempEvaluation.of(empNo, request, previous));
    }
}
