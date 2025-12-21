package com.admeliora.briefbot.application.client.service;

import com.admeliora.briefbot.application.client.model.Client;
import com.admeliora.briefbot.application.client.port.in.UpdateClientPort;
import com.admeliora.briefbot.application.client.port.in.command.UpdateClientCommand;
import com.admeliora.briefbot.application.client.port.out.ClientPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateClientService implements UpdateClientPort {

    private final ClientPort clientPort;

    @Override
    @Transactional
    public Client update(UpdateClientCommand command) {
        Client client = clientPort.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Client not found: " + command.id()));

        client.setName(command.name());
        client.setEmail(command.email());
        client.setCompanyName(command.companyName());
        client.setIndustry(command.industry());

        return clientPort.save(client);
    }
}

