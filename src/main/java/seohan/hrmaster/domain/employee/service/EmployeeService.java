package seohan.hrmaster.domain.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.hrmaster.domain.employee.dto.request.EmployeeRequestDTO;
import seohan.hrmaster.domain.employee.dto.response.EmployeePageResponseDTO;
import seohan.hrmaster.domain.employee.dto.response.EmployeeResponseDTO;
import seohan.hrmaster.domain.employee.entity.Employee;
import seohan.hrmaster.domain.employee.repository.EmployeeRepository;
import seohan.hrmaster.domain.global.exception.CustomException;
import seohan.hrmaster.domain.global.exception.ErrorCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void employeeCreate(EmployeeRequestDTO employeeRequestDTO){

        Employee employee = new Employee();

        employee.employCreate(
                generateUniqueEmpNUM(),
                employeeRequestDTO.getName(),
                employeeRequestDTO.getAddress(),
                employeeRequestDTO.getAddress2(),
                employeeRequestDTO.getPhoneNum(),
                employeeRequestDTO.getEmail(),
                employeeRequestDTO.getHireDate(),
                employeeRequestDTO.getSalary(),
                employeeRequestDTO.getEmRank(),
                "재직중"
        );

        employeeRepository.save(employee);
    }

    public int generateUniqueEmpNUM() {

        Random random = new Random();

        // YYMM 포맷 생성 (ex: 2025년 2월 → 2502)
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));

        int uniqueId;

        do {
            int randomPart = random.nextInt(10000); // 0000 ~ 9999
            uniqueId = Integer.parseInt(datePart + String.format("%04d", randomPart));
        } while (employeeRepository.existsByEmpNUM(uniqueId));

        return uniqueId;
    }

    public EmployeePageResponseDTO getAllEmployee(Pageable pageable){

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        List<EmployeeResponseDTO> employeeResponseDTOList =
                employeePage.stream()
                        .map(EmployeeResponseDTO::new)
                        .toList();

        return new EmployeePageResponseDTO(
                employeePage.getNumber()+1,
                employeePage.getTotalPages(),
                employeePage.getTotalElements(),
                employeePage.getSize(),
                employeeResponseDTOList);
    }

    public EmployeeResponseDTO getEmployeeById(Long employeeId){

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return new EmployeeResponseDTO(employee);
    }

    public EmployeeResponseDTO employeeUpdate(Long employeeId,
                                              EmployeeRequestDTO employeeRequestDTO){

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND));

        employee.setEmployeeDetails(
                employeeRequestDTO.getName(),
                employeeRequestDTO.getAddress(),
                employeeRequestDTO.getAddress2(),
                employeeRequestDTO.getPhoneNum(),
                employeeRequestDTO.getEmail(),
                employeeRequestDTO.getHireDate(),
                employeeRequestDTO.getSalary(),
                employeeRequestDTO.getEmRank(),
                employeeRequestDTO.getStatus()
        );

        employeeRepository.save(employee);

        return new EmployeeResponseDTO(employee);
    }
}
