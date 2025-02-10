package seohan.hrmaster.domain.auth.dto;

import lombok.Getter;
import seohan.hrmaster.domain.auth.entity.Admin;

@Getter
public class AuthLoginResponseDTO {

    private final Long adminId;

    private final String loginId;

    private final String accessToken;

    public AuthLoginResponseDTO(Admin admin, String accessToken) {
        this.adminId = admin.getAdminId();
        this.loginId = admin.getLoginId();
        this.accessToken = accessToken;
    }
}
