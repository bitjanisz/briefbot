package com.admeliora.briefbot.e2e.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Response model for account
 */
@Data
public class AccountResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}

