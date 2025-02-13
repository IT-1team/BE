package seohan.hrmaster.domain.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seohan.hrmaster.domain.employee.dto.request.EmployeeRequestDTO;
import seohan.hrmaster.domain.employee.entity.Employee;
import seohan.hrmaster.domain.employee.repository.EmployeeRepository;
import seohan.hrmaster.domain.employee.service.EmployeeService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository; // DB Mocking

    @InjectMocks
    private EmployeeService employeeService; // 테스트할 대상

    private EmployeeRequestDTO employeeRequestDTO;

    @BeforeEach
    void setUp() {
        employeeRequestDTO = new EmployeeRequestDTO(
                "홍길동",
                "서울특별시 강남구",
                "강남대로 123",
                "010-1234-5678",
                "test@example.com",
                LocalDate.of(2025, 2, 13),
                "5000",
                "대리",
                " "
        );
    }

    @Test
    void employeeCreate() {
        // Given
        when(employeeRepository.existsByEmpNUM(anyInt())).thenReturn(false); // 사번 중복 없음

        // When
        employeeService.employeeCreate(employeeRequestDTO);

        // Then
        verify(employeeRepository, times(1)).save(any(Employee.class)); // 저장이 호출되었는지 확인
    }

    @Test
    void generateUniqueEmpNUM() {
        // Given
        when(employeeRepository.existsByEmpNUM(anyInt())).thenReturn(false); // 중복 없음

        // When
        int empNum1 = employeeService.generateUniqueEmpNUM();
        int empNum2 = employeeService.generateUniqueEmpNUM();

        // Then
        assertThat(empNum1).isNotEqualTo(empNum2); // 두 개의 사번이 다름
    }
}
