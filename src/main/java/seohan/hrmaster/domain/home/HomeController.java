package seohan.hrmaster.domain.home;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import seohan.hrmaster.domain.global.response.ApiResponse;
import seohan.hrmaster.domain.global.response.GlobalResponse;

@RestController("/api/home")
public class HomeController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> heathlyCheck(){
        return GlobalResponse.OK("헬시체크 성공",null);
    }
}
