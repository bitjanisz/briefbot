package com.admeliora.briefbot.application.offer.service;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.briefing.port.out.BriefingPort;
import com.admeliora.briefbot.application.client.port.out.ClientPort;
import com.admeliora.briefbot.application.common.exception.NoAccountAssignedException;
import com.admeliora.briefbot.application.offer.model.Offer;
import com.admeliora.briefbot.application.offer.port.in.CreateOfferPort;
import com.admeliora.briefbot.application.offer.port.in.command.CreateOfferCommand;
import com.admeliora.briefbot.application.offer.port.out.OfferPort;
import com.admeliora.briefbot.infrastructure.context.AccountFilterContext;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOfferService implements CreateOfferPort {

    private final OfferPort offerPort;
    private final AccountPort accountPort;
    private final ClientPort clientPort;
    private final BriefingPort briefingPort;
    private final AccountFilterContext accountFilterContext;

    @Override
    @Transactional
    public Offer create(CreateOfferCommand command) {
        if (accountFilterContext.getAccountId() == null) {
            throw new NoAccountAssignedException(accountFilterContext.getUserEmail());
        }
        if (!accountPort.existsById(accountFilterContext.getAccountId())) {
            throw new EntityNotFoundException("Account not found: " + accountFilterContext.getAccountId());
        }
        if (!clientPort.existsById(command.clientId())) {
            throw new EntityNotFoundException("Client not found: " + command.clientId());
        }

        Offer.OfferBuilder builder = Offer.builder()
                .accountId(accountFilterContext.getAccountId())
                .clientId(command.clientId())
                .currentStatus(command.currentStatus());

        if (command.briefingId() != null) {
            if (!briefingPort.existsById(command.briefingId())) {
                throw new EntityNotFoundException("Briefing not found: " + command.briefingId());
            }
            builder.briefingId(command.briefingId());
        }

        return offerPort.save(builder.build());
    }
}

