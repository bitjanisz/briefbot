package com.admeliora.briefbot.application.offer.port.in;

import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.port.in.command.CreateOfferCommand;

public interface CreateOfferPort {
    Offer create(CreateOfferCommand command);
}

