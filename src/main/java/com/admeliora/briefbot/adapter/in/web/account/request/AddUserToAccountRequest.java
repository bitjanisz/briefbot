package com.admeliora.briefbot.adapter.in.web.account.request;

import com.admeliora.briefbot.domain.account.AccountRole;
import jakarta.validation.constraints.NotNull;

public record AddUserToAccountRequest(
        @NotNull
        Long accountId,
        @NotNull
        Long userId,
        @NotNull
        AccountRole role
) {
}