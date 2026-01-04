package com.admeliora.briefbot.application.client.service;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.client.model.Client;
import com.admeliora.briefbot.application.client.port.in.CreateClientPort;
import com.admeliora.briefbot.application.client.port.in.command.CreateClientCommand;
import com.admeliora.briefbot.application.client.port.out.ClientPort;
import com.admeliora.briefbot.application.common.exception.NoAccountAssignedException;
import com.admeliora.briefbot.infrastructure.context.AccountFilterContext;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateClientService implements CreateClientPort {

    private final ClientPort clientPort;
    private final AccountPort accountPort;
    private final AccountFilterContext accountFilterContext;

    @Override
    @Transactional
    public Client create(CreateClientCommand command) {
        if (accountFilterContext.getAccountId() == null) {
            throw new NoAccountAssignedException(accountFilterContext.getUserEmail());
        }
        if (!accountPort.existsById(accountFilterContext.getAccountId())) {
            throw new EntityNotFoundException("Account not found: " + accountFilterContext.getAccountId());
        }

        Client client = Client.builder()
                .accountId(accountFilterContext.getAccountId())
                .name(command.name())
                .email(command.email())
                .companyName(command.companyName())
                .industry(command.industry())
                .build();

        return clientPort.save(client);
    }
}

