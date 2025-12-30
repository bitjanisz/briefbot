package com.admeliora.briefbot.application.offer.port.in;

import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.port.in.command.UpdateOfferCommand;

public interface UpdateOfferPort {
    Offer update(UpdateOfferCommand command);
}

