package com.admeliora.briefbot.application.offer.port.out;

import com.admeliora.briefbot.application.offer.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferPort {
    Offer save(Offer offer);
    Optional<Offer> findById(Long id);
    List<Offer> findAll();
    boolean existsById(Long id);
    Offer getReferenceById(Long id);
    void delete(Offer offer);
}

