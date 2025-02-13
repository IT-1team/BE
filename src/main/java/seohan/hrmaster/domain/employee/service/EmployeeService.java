package seohan.hrmaster.domain.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.hrmaster.domain.employee.dto.request.EmployeeRequestDTO;
import seohan.hrmaster.domain.employee.entity.Employee;
import seohan.hrmaster.domain.employee.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

}
