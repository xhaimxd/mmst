package com.roger.mmst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MmstApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmstApplication.class, args);
    }

}
