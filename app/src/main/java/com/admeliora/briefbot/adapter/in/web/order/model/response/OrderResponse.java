package com.admeliora.briefbot.adapter.in.web.order.model.response;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long accountId,
        Long offerVersionId,
        String contractStatus,
        String contractFileUrl,
        LocalDateTime signedAt,
        LocalDateTime createdAt
) {
}

