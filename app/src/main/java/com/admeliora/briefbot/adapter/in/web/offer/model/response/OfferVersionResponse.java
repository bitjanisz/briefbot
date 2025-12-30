package com.admeliora.briefbot.adapter.in.web.offer.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OfferVersionResponse(
        Long id,
        Long offerId,
        Integer versionNumber,
        String introductionContent,
        String scopeContent,
        String methodologyContent,
        String summaryContent,
        BigDecimal totalNetto,
        BigDecimal totalBrutto,
        String currency,
        Boolean hasSpellingErrors,
        String aiSuggestions,
        LocalDateTime createdAt,
        List<OfferVersionItemResponse> items
) {
}

