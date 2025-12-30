package com.admeliora.briefbot.application.client.service;

import com.admeliora.briefbot.application.client.model.Client;
import com.admeliora.briefbot.application.client.port.in.ListClientsPort;
import com.admeliora.briefbot.application.client.port.out.ClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListClientsService implements ListClientsPort {

    private final ClientPort clientPort;

    @Override
    @Transactional(readOnly = true)
    public List<Client> listByAccountId(Long accountId) {
        return clientPort.findAll();
    }
}

