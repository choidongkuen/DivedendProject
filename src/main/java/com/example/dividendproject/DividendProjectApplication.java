package com.example.dividendproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DividendProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendProjectApplication.class, args);

    }
}
