package com.admeliora.briefbot.e2e.model.request;

import lombok.Builder;
import lombok.Data;

/**
 * Request model for creating an account
 */
@Data
@Builder
public class AccountRequest {
    private String name;
    private Long ownerId;
}

