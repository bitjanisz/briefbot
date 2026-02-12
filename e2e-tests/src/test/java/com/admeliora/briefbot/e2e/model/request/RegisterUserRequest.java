package com.admeliora.briefbot.e2e.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * Request model for user registration
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterUserRequest {
    private String email;
    private String givenName;
    private String familyName;
}

