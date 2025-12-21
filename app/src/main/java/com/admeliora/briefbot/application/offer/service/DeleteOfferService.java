package com.admeliora.briefbot.application.offer.service;

import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.port.in.DeleteOfferPort;
import com.admeliora.briefbot.application.offer.port.out.OfferPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteOfferService implements DeleteOfferPort {

    private final OfferPort offerPort;

    @Override
    @Transactional
    public void delete(Long id) {
        Offer offer = offerPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found: " + id));
        offerPort.delete(offer);
    }
}

