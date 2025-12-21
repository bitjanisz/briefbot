package com.admeliora.briefbot.application.offer.service;

import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.port.in.ListOffersPort;
import com.admeliora.briefbot.application.offer.port.out.OfferPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListOffersService implements ListOffersPort {

    private final OfferPort offerPort;

    @Override
    @Transactional(readOnly = true)
    public List<Offer> listByAccountId(Long accountId) {
        return offerPort.findAll();
    }
}

