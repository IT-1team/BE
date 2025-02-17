package seohan.hrmaster.domain.attendance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import seohan.hrmaster.domain.attendance.dto.response.AttendancePageResponseDTO;
import seohan.hrmaster.domain.attendance.service.AttendanceService;
import seohan.hrmaster.domain.global.response.ApiResponse;
import seohan.hrmaster.domain.global.response.GlobalResponse;

import java.time.LocalDate;

@RequestMapping("/api/attendance")
@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<AttendancePageResponseDTO>> getAllAttendance(
            @RequestParam(name = "date") LocalDate today,
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable) {

        int correctedPage = (pageable.getPageNumber() > 0) ? pageable.getPageNumber() - 1 : 0;

        Pageable updatedPageable =
                PageRequest.of(correctedPage, pageable.getPageSize(), pageable.getSort());

        AttendancePageResponseDTO attendancePageResponseDTO =
                attendanceService.getAllAttendance(today, updatedPageable);

        return GlobalResponse.OK("출퇴근 조회 성공", attendancePageResponseDTO);
    }


}
