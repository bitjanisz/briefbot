package com.admeliora.briefbot.adapter.out.persistence.offer;

import com.admeliora.briefbot.adapter.out.persistence.offer.jpa.OfferRepositoryJpa;
import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.port.out.OfferPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OfferAdapter implements OfferPort {

    private final OfferRepositoryJpa offerRepository;

    @Override
    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Optional<Offer> findById(Long id) {
        return offerRepository.findById(id);
    }

    @Override
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return offerRepository.existsById(id);
    }

    @Override
    public Offer getReferenceById(Long id) {
        return offerRepository.getReferenceById(id);
    }

    @Override
    public void delete(Offer offer) {
        offerRepository.delete(offer);
    }
}

