package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAccountRequest {
    private Long userId;
    private Long accountId;
    private String role;
}
