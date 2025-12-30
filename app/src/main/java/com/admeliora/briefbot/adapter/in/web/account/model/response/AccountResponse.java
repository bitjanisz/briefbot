package com.admeliora.briefbot.adapter.in.web.account.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record AccountResponse(
        Long id,
        String name,
        LocalDateTime createdAt
) {}
