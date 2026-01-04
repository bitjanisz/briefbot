package com.admeliora.briefbot.application.client.service;

import com.admeliora.briefbot.application.client.model.Client;
import com.admeliora.briefbot.application.client.port.in.GetClientPort;
import com.admeliora.briefbot.application.client.port.out.ClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetClientService implements GetClientPort {

    private final ClientPort clientPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> getById(Long id) {
        return clientPort.findById(id);
    }
}

