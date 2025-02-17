package seohan.hrmaster.domain.attendance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import seohan.hrmaster.domain.employee.entity.Employee;
import seohan.hrmaster.domain.global.entity.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Attendance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private LocalDate today;

    private LocalDateTime clockIn;

    private LocalDateTime clockOut;
}
