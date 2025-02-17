package seohan.hrmaster.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import seohan.hrmaster.domain.attendance.dto.response.AttendancePageResponseDTO;
import seohan.hrmaster.domain.attendance.dto.response.AttendanceResponseDTO;
import seohan.hrmaster.domain.attendance.entity.Attendance;
import seohan.hrmaster.domain.attendance.repository.AttendanceRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendancePageResponseDTO getAllAttendance(LocalDate today, Pageable pageable) {

        Page<Attendance> attendancePage = attendanceRepository.findByToday(today, pageable);

        List<AttendanceResponseDTO> responseDTOList =
                attendancePage.stream()
                        .map(
                                attendance ->
                                        new AttendanceResponseDTO(attendance, getClockStatus(attendance))
                        )
                        .toList();

        return new AttendancePageResponseDTO(
                attendancePage.getNumber() + 1,
                attendancePage.getTotalPages(),
                attendancePage.getTotalElements(),
                attendancePage.getSize(),
                responseDTOList
        );
    }

    private String getClockStatus(Attendance attendance) {

        if (attendance.getClockIn() == null || attendance.getClockOut() == null) {
            return "이상"; // 출퇴근 시간이 하나라도 없으면 "이상" 처리
        }

        Duration workDuration = Duration.between(attendance.getClockIn(), attendance.getClockOut());
        long workHours = workDuration.toMinutes() / 60; // 분 단위로 변환 후 시간 변환

        return workHours >= 9 ? "정상" : "이상";
    }
}
