package com.admeliora.briefbot.adapter.in.web.offer.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OfferVersionItemResponse(
        Long id,
        Long offerVersionId,
        Long originalServiceId,
        String serviceName,
        String description,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal vatRate,
        LocalDateTime createdAt
) {
}

