package seohan.hrmaster.domain.employee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import seohan.hrmaster.domain.global.entity.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Getter
public class Employee extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private int empNUM;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String address2;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate hireDate;

    @Column(nullable = false)
    private String salary;

    @Column(nullable = false)
    private String emRank;

    @Column(nullable = false)
    private String status;

    public void setEmployeeDetails(
            Department department, String name, String address, String address2, String phoneNum,
            String email, LocalDate hireDate, String salary, String emRank, String status) {

        this.department = department;
        this.name = name;
        this.address = address;
        this.address2 = address2;
        this.phoneNum = phoneNum;
        this.email = email;
        this.hireDate = hireDate;
        this.salary = salary;
        this.emRank = emRank;
        this.status = status;
    }

    public void employCreate(
            Department department, int empNUM, String name, String address, String address2, String phoneNum,
            String email, LocalDate hireDate, String salary, String emRank, String status) {

        this.empNUM = empNUM;
        setEmployeeDetails(
                department, name, address, address2, phoneNum,
                email, hireDate, salary, emRank, status);
    }
}
