package seohan.hrmaster.domain.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("local")
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인 성공 테스트")
    public void testLoginSuccess() throws Exception {
        // 로그인 요청 객체
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("loginId", "admin01");
        loginRequest.put("password", "password123");

        // 로그인 API 호출
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk()) // HTTP 200 응답 확인
                .andExpect(jsonPath("$.message").value("로그인 성공"))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty()) // JWT 토큰 반환 확인
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    public void testLoginFail_WrongPassword() throws Exception {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("loginId", "admin01");
        loginRequest.put("password", "wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()) // HTTP 401 응답 확인
                .andExpect(jsonPath("$.message").value("로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다."))
                .andExpect(jsonPath("$.data").isEmpty()) // data 필드가 null인지 확인
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    public void testLoginFail_UserNotFound() throws Exception {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("loginId", "nonexistentUser");
        loginRequest.put("password", "password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("로그인 실패: 인증에 실패하였습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    public void testPasswordMatches() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "password123";

        // 새로운 비밀번호 해싱
        String newEncodedPassword = encoder.encode(rawPassword);

        // 해싱된 비밀번호 출력
        System.out.println("새로 생성된 해시값: " + newEncodedPassword);
    }
}
