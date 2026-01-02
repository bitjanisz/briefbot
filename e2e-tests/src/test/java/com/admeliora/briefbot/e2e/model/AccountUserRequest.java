package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountUserRequest {
    private Long userId;
    private String role;
}
