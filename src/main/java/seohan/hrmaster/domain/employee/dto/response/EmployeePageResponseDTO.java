package seohan.hrmaster.domain.employee.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class EmployeePageResponseDTO {

    private final int currentPage;     // 현재 페이지 번호

    private final int totalPages;      // 전체 페이지 수

    private final long totalElements;     // 전체 항목 수

    private final int pageSize;        // 한 페이지당 항목 수

    private final List<EmployeeResponseDTO> responseDTOList;

    public EmployeePageResponseDTO(int currentPage,
                                   int totalPages,
                                   long totalElements,
                                   int pageSize,
                                   List<EmployeeResponseDTO> responseDTOList) {

        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.responseDTOList = responseDTOList;
    }
}
