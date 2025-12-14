package com.admeliora.briefbot.account.api.dto;


import com.admeliora.briefbot.domain.account.AccountRole;

public record AddUserToAccountDto(
        Long accountId,
        Long userId,
        AccountRole role
) {}

