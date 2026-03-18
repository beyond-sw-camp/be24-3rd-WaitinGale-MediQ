package org.example.mediqback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MediQBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediQBackApplication.class, args);
    }
}
