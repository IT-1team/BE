package seohan.hrmaster.domain.attendance.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class AttendancePageResponseDTO {

    private final int currentPage;     // 현재 페이지 번호

    private final int totalPages;      // 전체 페이지 수

    private final long totalElements;     // 전체 항목 수

    private final int pageSize;        // 한 페이지당 항목 수

    private final List<AttendanceResponseDTO> responseDTOList;

    public AttendancePageResponseDTO(int currentPage,
                                     int totalPages,
                                     long totalElements,
                                     int pageSize,
                                     List<AttendanceResponseDTO> responseDTOList) {

        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.responseDTOList = responseDTOList;
    }
}
