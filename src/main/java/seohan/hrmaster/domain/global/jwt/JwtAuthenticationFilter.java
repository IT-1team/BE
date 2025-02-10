package seohan.hrmaster.domain.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import seohan.hrmaster.domain.auth.dto.AuthLoginRequestDTO;
import seohan.hrmaster.domain.auth.dto.AuthLoginResponseDTO;
import seohan.hrmaster.domain.global.response.ApiResponse;
import seohan.hrmaster.domain.global.user.UserDetailsImpl;

import java.io.IOException;


@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        log.info("로그인 시도");
        try {
            AuthLoginRequestDTO requestDTO =
                    new ObjectMapper().readValue(request.getInputStream(), AuthLoginRequestDTO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getLoginId(),
                            requestDTO.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error("요청 데이터 읽기 오류: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {

        UserDetailsImpl userDetails = ((UserDetailsImpl) authResult.getPrincipal());

        log.info("로그인 성공 및 JWT 생성");

        String loginId = userDetails.getAdmin().getLoginId();

        String accessToken = jwtUtil.createAccessToken(loginId);

        AuthLoginResponseDTO responseDTO = new AuthLoginResponseDTO(userDetails.getAdmin(), accessToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter()
                .write(new ObjectMapper().writeValueAsString(
                        ApiResponse.of("로그인 성공", responseDTO)));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException {

        // 로그인 실패 원인별 메시지 설정
        String errorMessage;
        if (failed instanceof BadCredentialsException) {
            errorMessage = "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.";
        } else if (failed instanceof DisabledException) {
            errorMessage = "로그인 실패: 계정이 비활성화되었습니다.";
        } else if (failed instanceof LockedException) {
            errorMessage = "로그인 실패: 계정이 잠겨 있습니다.";
        } else {
            errorMessage = "로그인 실패: 인증에 실패하였습니다.";
        }

        log.info("로그인 실패: {}", errorMessage);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 반환
        response.getWriter().write(new ObjectMapper()
                .writeValueAsString(ApiResponse.of(errorMessage, null)));
    }
}
