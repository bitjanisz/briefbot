package com.admeliora.briefbot.e2e.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * Request model for user login
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {
    private String email;
    private String password;
}

