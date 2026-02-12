package com.admeliora.briefbot.adapter.in.web.user.model.request;

import jakarta.validation.constraints.NotNull;

public record SwitchAccountRequest(
        @NotNull
        Long accountId
) {
}
