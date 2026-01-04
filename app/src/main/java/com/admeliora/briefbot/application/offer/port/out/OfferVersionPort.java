package com.admeliora.briefbot.application.offer.port.out;

import com.admeliora.briefbot.application.offer.model.OfferVersion;

import java.util.Optional;

public interface OfferVersionPort {
    OfferVersion save(OfferVersion offerVersion);

    Optional<OfferVersion> findById(Long id);

    boolean existsById(Long id);

    OfferVersion getReferenceById(Long id);
}

