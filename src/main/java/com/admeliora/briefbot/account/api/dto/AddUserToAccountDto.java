package com.admeliora.briefbot.account.api.dto;

import com.admeliora.briefbot.account.domain.AccountRole;

public record AddUserToAccountDto(
        Long accountId,
        Long userId,
        AccountRole role
) {}
