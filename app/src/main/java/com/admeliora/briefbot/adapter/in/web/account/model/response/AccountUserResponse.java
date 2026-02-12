package com.admeliora.briefbot.adapter.in.web.account.model.response;

import com.admeliora.briefbot.application.account.model.AccountRole;

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
