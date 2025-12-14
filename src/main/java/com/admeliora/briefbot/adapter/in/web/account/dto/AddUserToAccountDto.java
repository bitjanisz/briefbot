package com.admeliora.briefbot.adapter.in.web.account.dto;

import com.admeliora.briefbot.domain.account.AccountRole;
import jakarta.validation.constraints.NotNull;

public record AddUserToAccountDto(
    @NotNull
    Long accountId,
    @NotNull
    Long userId,
    @NotNull
    AccountRole role
) {}
// If you add String fields, use @Size(max = ...) for length validation.
