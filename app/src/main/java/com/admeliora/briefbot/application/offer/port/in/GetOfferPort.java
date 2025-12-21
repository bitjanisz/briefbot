package com.admeliora.briefbot.application.offer.port.in;

import com.admeliora.briefbot.application.offer.model.Offer;

import java.util.Optional;

public interface GetOfferPort {
    Optional<Offer> getById(Long id);
}

