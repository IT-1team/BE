package seohan.hrmaster.domain.attendance.dto.response;

import lombok.Getter;
import seohan.hrmaster.domain.attendance.entity.Attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class AttendanceResponseDTO {

    private final Long attendanceId;

    private final int empNum;

    private final String name;

    private final String departmentName;

    private final String teamName;

    private final String emRank;

    private final LocalDate today;

    private final LocalDateTime clockIn;

    private final LocalDateTime clockOut;

    private final String clockStatus;

    public AttendanceResponseDTO(Attendance attendance, String clockStatus) {
        this.attendanceId = attendance.getAttendanceId();
        this.empNum = attendance.getEmployee().getEmpNUM();
        this.name = attendance.getEmployee().getName();
        this.departmentName = attendance.getEmployee().getDepartment().getDepartmentName();
        this.teamName = attendance.getEmployee().getDepartment().getTeamName();
        this.emRank = attendance.getEmployee().getEmRank();
        this.today = attendance.getToday();
        this.clockIn = attendance.getClockIn();
        this.clockOut = attendance.getClockOut();
        this.clockStatus = clockStatus;
    }
}
