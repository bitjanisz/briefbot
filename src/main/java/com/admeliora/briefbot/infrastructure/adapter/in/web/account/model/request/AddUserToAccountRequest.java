package com.admeliora.briefbot.infrastructure.adapter.in.web.account.model.request;

import com.admeliora.briefbot.account.model.AccountRole;
import jakarta.validation.constraints.NotNull;

public record AddUserToAccountRequest(
        @NotNull Long accountId,
        @NotNull Long userId,
        @NotNull AccountRole role
) {
}