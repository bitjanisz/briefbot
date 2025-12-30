package com.admeliora.briefbot.application.offer.port.in;

import com.admeliora.briefbot.application.offer.model.Offer;

import java.util.List;

public interface ListOffersPort {
    List<Offer> listByAccountId(Long accountId);
}

