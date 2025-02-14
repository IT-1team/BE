package seohan.hrmaster.domain.employee.dto.response;

import lombok.Getter;
import seohan.hrmaster.domain.employee.entity.Employee;

import java.time.LocalDate;

@Getter
public class EmployeeResponseDTO {

    private Long employeeId;

    private int empNUM;

    private String name;

    private String address;

    private String address2;

    private String departmentName;

    private String teamName;

    private String phoneNum;

    private String email;

    private LocalDate hireDate;

    private String salary;

    private String emRank;

    private String status;

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