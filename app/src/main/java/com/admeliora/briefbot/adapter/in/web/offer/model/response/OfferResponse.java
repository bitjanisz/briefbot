package com.admeliora.briefbot.adapter.in.web.offer.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record OfferResponse(
        Long id,
        Long accountId,
        Long clientId,
        Long briefingId,
        String currentStatus,
        LocalDateTime createdAt,
        List<OfferVersionResponse> versions
) {
}

