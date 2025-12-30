package com.admeliora.briefbot.adapter.out.persistence.offer;

import com.admeliora.briefbot.adapter.out.persistence.offer.jpa.OfferVersionRepositoryJpa;
import com.admeliora.briefbot.application.offer.model.OfferVersion;
import com.admeliora.briefbot.application.offer.port.out.OfferVersionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OfferVersionAdapter implements OfferVersionPort {

    private final OfferVersionRepositoryJpa offerVersionRepository;

    @Override
    public OfferVersion save(OfferVersion offerVersion) {
        return offerVersionRepository.save(offerVersion);
    }

    @Override
    public Optional<OfferVersion> findById(Long id) {
        return offerVersionRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return offerVersionRepository.existsById(id);
    }

    @Override
    public OfferVersion getReferenceById(Long id) {
        return offerVersionRepository.getReferenceById(id);
    }
}

