package seohan.hrmaster.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthLoginRequestDTO {

    @Size(max = 20)
    @Email
    @NotBlank
    private String loginId;

    @Size(max = 20)
    @NotBlank
    private String password;
}
