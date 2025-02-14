package seohan.hrmaster.domain.employee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohan.hrmaster.domain.employee.dto.request.EmployeeRequestDTO;
import seohan.hrmaster.domain.employee.dto.response.EmployeePageResponseDTO;
import seohan.hrmaster.domain.employee.dto.response.EmployeeResponseDTO;
import seohan.hrmaster.domain.employee.service.EmployeeService;
import seohan.hrmaster.domain.global.response.ApiResponse;
import seohan.hrmaster.domain.global.response.GlobalResponse;

@RequestMapping("/api/employees")
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> employeeCreate(
            @RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) {

        employeeService.employeeCreate(employeeRequestDTO);

        return GlobalResponse.CREATED("사원 등록 성공", null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<EmployeePageResponseDTO>> getAllEmployee(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable) {

        int correctedPage = (pageable.getPageNumber() > 0) ? pageable.getPageNumber() - 1 : 0;

        Pageable updatedPageable =
                PageRequest.of(correctedPage, pageable.getPageSize(), pageable.getSort());

        EmployeePageResponseDTO employeePageResponseDTO = employeeService.getAllEmployee(updatedPageable);

        return GlobalResponse.OK("사원 목록 조회 성공", employeePageResponseDTO);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getEmployeeById(
            @PathVariable Long employeeId) {

        EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployeeById(employeeId);

        return GlobalResponse.OK("사원 상세 조회 성공",employeeResponseDTO);
    }
}
