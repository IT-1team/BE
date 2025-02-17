package seohan.hrmaster.domain.employee.dto.response;

import lombok.Getter;
import seohan.hrmaster.domain.employee.entity.Employee;

import java.time.LocalDate;

@Getter
public class EmployeeResponseDTO {

    private final Long employeeId;

    private final int empNUM;

    private final String name;

    private final String address;

    private final String address2;

    private final String departmentName;

    private final String teamName;

    private final String phoneNum;

    private final String email;

    private final LocalDate hireDate;

    private final String salary;

    private final String emRank;

    private final String status;

    public EmployeeResponseDTO(Employee employee) {
        this.status = employee.getStatus();
        this.emRank = employee.getEmRank();
        this.salary = employee.getSalary();
        this.hireDate = employee.getHireDate();
        this.email = employee.getEmail();
        this.phoneNum = employee.getPhoneNum();
        this.departmentName = employee.getDepartment().getDepartmentName();
        this.teamName = employee.getDepartment().getTeamName();
        this.address2 = employee.getAddress2();
        this.address = employee.getAddress();
        this.name = employee.getName();
        this.empNUM = employee.getEmpNUM();
        this.employeeId = employee.getEmployeeId();
    }
}