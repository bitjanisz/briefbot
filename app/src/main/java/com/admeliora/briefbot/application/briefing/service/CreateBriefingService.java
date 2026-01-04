package com.admeliora.briefbot.application.briefing.service;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.briefing.model.Briefing;
import com.admeliora.briefbot.application.briefing.port.in.CreateBriefingPort;
import com.admeliora.briefbot.application.briefing.port.in.command.CreateBriefingCommand;
import com.admeliora.briefbot.application.briefing.port.out.BriefingPort;
import com.admeliora.briefbot.application.client.port.out.ClientPort;
import com.admeliora.briefbot.application.common.exception.NoAccountAssignedException;
import com.admeliora.briefbot.infrastructure.context.AccountFilterContext;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBriefingService implements CreateBriefingPort {

    private final BriefingPort briefingPort;
    private final AccountPort accountPort;
    private final ClientPort clientPort;
    private final AccountFilterContext accountFilterContext;

    @Override
    @Transactional
    public Briefing create(CreateBriefingCommand command) {
        if (accountFilterContext.getAccountId() == null) {
            throw new NoAccountAssignedException(accountFilterContext.getUserEmail());
        }
        // Validate account exists
        if (!accountPort.existsById(accountFilterContext.getAccountId())) {
            throw new EntityNotFoundException("Account not found: " + accountFilterContext.getAccountId());
        }

        // Validate client exists
        if (!clientPort.existsById(command.clientId())) {
            throw new EntityNotFoundException("Client not found: " + command.clientId());
        }

        Briefing briefing = Briefing.builder()
                .accountId(accountFilterContext.getAccountId())
                .clientId(command.clientId())
                .status(command.status())
                .build();

        return briefingPort.save(briefing);
    }
}

