package com.admeliora.briefbot.adapter.in.web.account.model.response;

import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String name,
        LocalDateTime createdAt
) {
}
