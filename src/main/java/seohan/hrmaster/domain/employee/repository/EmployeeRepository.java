package seohan.hrmaster.domain.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import seohan.hrmaster.domain.employee.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmpNUM(int empNUM);
}
