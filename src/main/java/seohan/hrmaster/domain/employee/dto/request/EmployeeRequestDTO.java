package seohan.hrmaster.domain.employee.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    private String address2;

    @NotBlank
    @Size(max = 13)
    private String phoneNum;

    @NotBlank
    @Email(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @NotNull
    private LocalDate hireDate;

    @NotBlank
    private String salary;

    @NotBlank
    private String emRank;

    @NotBlank
    private String status;
}
