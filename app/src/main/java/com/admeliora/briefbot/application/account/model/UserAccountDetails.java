package com.admeliora.briefbot.application.account.model;

import java.time.LocalDateTime;

public record UserAccountDetails(
        Long id,
        Long userId,
        String userEmail,
        String userGivenName,
        String userFamilyName,
        AccountRole role,
        LocalDateTime createdAt
) {
}
