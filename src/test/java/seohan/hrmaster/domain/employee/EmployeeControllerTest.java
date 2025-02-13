package seohan.hrmaster.domain.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import seohan.hrmaster.domain.employee.dto.request.EmployeeRequestDTO;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void employeeCreate() throws Exception {
        // Given
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                "홍길동",
                "서울특별시 강남구",
                "강남대로 123",
                "010-1234-5678",
                "test@example.com",
                LocalDate.of(2025, 2, 13),
                "5000",
                "대리",
                "아무값"
        );

        // When & Then
        mockMvc.perform(post("/api/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()) // 201 Created 확인
                .andExpect(jsonPath("$.message").value("사원 등록 성공"))
                .andDo(print());
    }
}