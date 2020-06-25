package br.com.marcia.error;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ErrorCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorCenterApplication.class, args);
    }

}
