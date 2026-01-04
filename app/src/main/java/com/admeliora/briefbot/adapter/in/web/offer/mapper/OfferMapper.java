package com.admeliora.briefbot.adapter.in.web.offer.mapper;

import com.admeliora.briefbot.adapter.in.web.offer.model.response.OfferResponse;
import com.admeliora.briefbot.adapter.in.web.offer.model.response.OfferVersionItemResponse;
import com.admeliora.briefbot.adapter.in.web.offer.model.response.OfferVersionResponse;
import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.model.OfferVersion;
import com.admeliora.briefbot.application.offer.model.OfferVersionItem;

import java.util.List;
import java.util.stream.Collectors;

public class OfferMapper {

    public static OfferResponse toResponse(Offer offer) {
        if (offer == null) return null;

        List<OfferVersionResponse> versions = offer.getVersions() != null
                ? offer.getVersions().stream()
                .map(OfferMapper::toVersionResponse)
                .collect(Collectors.toList())
                : List.of();

        return new OfferResponse(
                offer.getId(),
                offer.getAccountId(),
                offer.getClientId(),
                offer.getBriefingId(),
                offer.getCurrentStatus(),
                offer.getCreatedAt(),
                versions
        );
    }

    public static OfferVersionResponse toVersionResponse(OfferVersion version) {
        if (version == null) return null;

        List<OfferVersionItemResponse> items = version.getItems() != null
                ? version.getItems().stream()
                .map(OfferMapper::toItemResponse)
                .collect(Collectors.toList())
                : List.of();

        return new OfferVersionResponse(
                version.getId(),
                version.getOffer() != null ? version.getOffer().getId() : null,
                version.getVersionNumber(),
                version.getIntroductionContent(),
                version.getScopeContent(),
                version.getMethodologyContent(),
                version.getSummaryContent(),
                version.getTotalNetto(),
                version.getTotalBrutto(),
                version.getCurrency(),
                version.getHasSpellingErrors(),
                version.getAiSuggestions(),
                version.getCreatedAt(),
                items
        );
    }

    public static OfferVersionItemResponse toItemResponse(OfferVersionItem item) {
        if (item == null) return null;
        return new OfferVersionItemResponse(
                item.getId(),
                item.getOfferVersion() != null ? item.getOfferVersion().getId() : null,
                item.getOriginalServiceId(),
                item.getServiceName(),
                item.getDescription(),
                item.getQuantity(),
                item.getPrice(),
                item.getVatRate(),
                item.getCreatedAt()
        );
    }
}

