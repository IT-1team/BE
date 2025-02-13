package seohan.hrmaster.domain.employee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seohan.hrmaster.domain.employee.dto.request.EmployeeRequestDTO;
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


}
