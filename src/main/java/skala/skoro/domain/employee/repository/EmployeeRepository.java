package skala.skoro.domain.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skala.skoro.domain.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
