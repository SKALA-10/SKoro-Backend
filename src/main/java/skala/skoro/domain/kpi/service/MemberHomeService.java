package skala.skoro.domain.kpi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skala.skoro.domain.employee.dto.EmployeeSummaryResponse;
import skala.skoro.domain.employee.repository.EmployeeRepository;
import skala.skoro.domain.kpi.dto.MyTaskResponse;
import skala.skoro.domain.kpi.entity.Task;
import skala.skoro.domain.kpi.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberHomeService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public List<MyTaskResponse> getMyTasks(String empNo) {
        int thisYear = LocalDate.now().getYear();
        List<Task> myTasks = taskRepository.findMyTasksThisYear(empNo, thisYear);

        // 각 Task별로 멤버 리스트 조립
        return myTasks.stream().map(task -> {
            // 1. KPI명
            String kpiName = task.getTeamKpi().getKpiName();

            // 2. 같은 task_id로 팀원 조회
            List<Task> members = taskRepository.findByTaskId(task.getId());
            List<EmployeeSummaryResponse> memberDtos = members.stream().map(t -> {
                var emp = employeeRepository.findById(t.getEmployee().getEmpNo()).orElse(null);
                return EmployeeSummaryResponse.builder()
                        .empNo(t.getEmployee().getEmpNo())
                        .empName(emp != null ? emp.getEmpName() : null)
                        .profileImage(emp != null ? emp.getProfileImage() : null)
                        .build();
            }).toList();

            return MyTaskResponse.builder()
                    .taskId(task.getId())
                    .taskName(task.getTaskName())
                    .targetLevel(task.getTargetLevel())
                    .kpiName(kpiName)
                    .startDate(task.getStartDate())
                    .endDate(task.getEndDate())
                    .teamMembers(memberDtos)
                    .build();
        }).toList();
    }
}
