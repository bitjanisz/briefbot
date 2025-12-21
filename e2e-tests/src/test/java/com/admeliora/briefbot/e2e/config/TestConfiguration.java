package com.admeliora.briefbot.e2e.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring configuration for E2E tests
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.admeliora.briefbot.e2e")
public class TestConfiguration {
}

