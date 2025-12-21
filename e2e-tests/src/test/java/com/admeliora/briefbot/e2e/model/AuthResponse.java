package com.admeliora.briefbot.e2e.model;

import lombok.Data;

/**
 * Response model for authentication (registration and login)
 */
@Data
public class AuthResponse {
    private Long userId;
    private String email;
    private String givenName;
    private String familyName;
    private String message;
}

