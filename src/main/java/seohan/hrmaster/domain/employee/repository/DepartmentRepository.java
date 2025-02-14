package seohan.hrmaster.domain.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seohan.hrmaster.domain.employee.entity.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentNameAndTeamName(String departmentName, String teamName);
}
