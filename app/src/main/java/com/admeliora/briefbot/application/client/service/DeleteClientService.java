package com.admeliora.briefbot.application.client.service;

import com.admeliora.briefbot.application.client.model.Client;
import com.admeliora.briefbot.application.client.port.in.DeleteClientPort;
import com.admeliora.briefbot.application.client.port.out.ClientPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteClientService implements DeleteClientPort {

    private final ClientPort clientPort;

    @Override
    @Transactional
    public void delete(Long id) {
        Client client = clientPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: " + id));
        clientPort.delete(client);
    }
}

