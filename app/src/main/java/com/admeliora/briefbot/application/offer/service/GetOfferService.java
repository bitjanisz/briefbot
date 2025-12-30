package com.admeliora.briefbot.application.offer.service;

import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.port.in.GetOfferPort;
import com.admeliora.briefbot.application.offer.port.out.OfferPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetOfferService implements GetOfferPort {

    private final OfferPort offerPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> getById(Long id) {
        return offerPort.findById(id);
    }
}

