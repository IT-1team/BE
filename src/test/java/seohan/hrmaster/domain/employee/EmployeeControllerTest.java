package seohan.hrmaster.domain.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import seohan.hrmaster.domain.employee.dto.request.EmployeeRequestDTO;
import seohan.hrmaster.domain.employee.repository.EmployeeRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    private Long latestEmployeeId;

    @BeforeEach
    void setup() {
        latestEmployeeId = employeeRepository.findLatestEmployeeId();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    @DisplayName("사원 등록 API - 성공")
    void employeeCreate() throws Exception {
        // Given
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                "홍길동",
                "서울특별시 강남구",
                "강남대로 123",
                "기획본부",
                "IT기획",
                "010-1234-5678",
                "test@example.com",
                LocalDate.of(2025, 2, 13),
                "5000",
                "대리",
                "재직중"
        );

        // When & Then
        mockMvc.perform(post("/api/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()) // 201 Created 확인
                .andExpect(jsonPath("$.message").value("사원 등록 성공"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    @DisplayName("사원 목록 조회 API - 성공")
    void getAllEmployee_Success() throws Exception {
        mockMvc.perform(get("/api/employees")
                        .param("page", "1")  // 첫 번째 페이지 요청
                        .param("size", "10") // 10개씩 조회
                        .param("sort", "createdAt,desc") // 최신순 정렬
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 응답 확인
                .andExpect(jsonPath("$.message").value("사원 목록 조회 성공")) // 응답 메시지 검증
                .andExpect(jsonPath("$.data.currentPage").isNumber()) // 페이지 번호 확인
                .andExpect(jsonPath("$.data.totalPages").isNumber()) // 전체 페이지 수 확인
                .andExpect(jsonPath("$.data.totalElements").isNumber()) // 전체 데이터 개수 확인
                .andExpect(jsonPath("$.data.pageSize").value(10)) // 한 페이지 크기 검증
                .andExpect(jsonPath("$.data.responseDTOList").isArray()) // employees가 배열인지 확인
                .andExpect(jsonPath("$.data.responseDTOList[0].name").isString()) // 첫 번째 직원의 이름이 존재하는지 확인
                .andExpect(jsonPath("$.data.responseDTOList[0].email").isString()); // 첫 번째 직원의 이메일이 존재하는지 확인
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    @DisplayName("사원 ID로 조회 - 성공")
    void getEmployeeById_Success() throws Exception {

        Long latestEmployeeId = employeeRepository.findLatestEmployeeId();

        // When & Then (API 호출 및 검증)
        mockMvc.perform(get("/api/employees/{employeeId}", latestEmployeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // HTTP 200 응답 확인
                .andExpect(jsonPath("$.message").value("사원 상세 조회 성공"))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data.employeeId").isNumber())  // ID 확인
                .andExpect(jsonPath("$.data.name").value("홍길동"))  // 사원 이름 검증
                .andExpect(jsonPath("$.data.email").value("test@example.com"))  // 이메일 검증
                .andExpect(jsonPath("$.data.salary").value("5000"));  // 급여 검증
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    @DisplayName("사원 수정 - 성공")
    void employeeUpdate_Success() throws Exception {

        // 수정할 데이터
        EmployeeRequestDTO updateRequestDTO = new EmployeeRequestDTO(
                "김철수",
                "부산광역시",
                "강남대로 123",
                "기획본부",
                "인사",
                "010-5678-1234",
                "kim@example.com",
                LocalDate.of(2025, 2, 13),
                "6000",
                "과장",
                "퇴사"
        );

        // When & Then
        mockMvc.perform(put("/api/employees/{employeeId}", latestEmployeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("사원 수정 성공"))
                .andExpect(jsonPath("$.data.name").value("김철수"))
                .andExpect(jsonPath("$.data.address").value("부산광역시"))
                .andExpect(jsonPath("$.data.phoneNum").value("010-5678-1234"))
                .andExpect(jsonPath("$.data.email").value("kim@example.com"))
                .andExpect(jsonPath("$.data.salary").value("6000"))
                .andExpect(jsonPath("$.data.emRank").value("과장"))
                .andExpect(jsonPath("$.data.status").value("퇴사"));
    }
}