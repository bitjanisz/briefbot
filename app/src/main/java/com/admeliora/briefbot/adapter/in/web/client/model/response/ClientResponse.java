package com.admeliora.briefbot.adapter.in.web.client.model.response;

import java.time.LocalDateTime;

public record ClientResponse(
        Long id,
        Long accountId,
        String name,
        String email,
        String companyName,
        String industry,
        LocalDateTime createdAt
) {
}

