package com.admeliora.briefbot.e2e.model.response;

import com.admeliora.briefbot.e2e.model.AccountRole;

import java.time.LocalDateTime;

public record AccountUserResponse(
        Long id,
        Long userId,
        String userEmail,
        String userGivenName,
        String userFamilyName,
        AccountRole role,
        LocalDateTime createdAt
) {
}
