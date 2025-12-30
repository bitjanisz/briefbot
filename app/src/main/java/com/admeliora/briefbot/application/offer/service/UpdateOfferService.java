package com.admeliora.briefbot.application.offer.service;

import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.port.in.UpdateOfferPort;
import com.admeliora.briefbot.application.offer.port.in.command.UpdateOfferCommand;
import com.admeliora.briefbot.application.offer.port.out.OfferPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOfferService implements UpdateOfferPort {

    private final OfferPort offerPort;

    @Override
    @Transactional
    public Offer update(UpdateOfferCommand command) {
        Offer offer = offerPort.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Offer not found: " + command.id()));

        offer.setCurrentStatus(command.currentStatus());

        return offerPort.save(offer);
    }
}

