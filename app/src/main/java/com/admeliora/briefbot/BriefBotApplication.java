package com.admeliora.briefbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.admeliora.briefbot")
@EnableJpaRepositories(basePackages = "com.admeliora.briefbot.adapter.out.persistence")
@EntityScan(basePackages = "com.admeliora.briefbot.application")
public class BriefBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BriefBotApplication.class, args);
    }

}
