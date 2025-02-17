package seohan.hrmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HrMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrMasterApplication.class, args);
    }

}
