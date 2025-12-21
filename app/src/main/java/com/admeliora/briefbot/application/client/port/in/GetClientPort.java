package com.admeliora.briefbot.application.client.port.in;

import com.admeliora.briefbot.application.client.model.Client;

import java.util.Optional;

public interface GetClientPort {
    Optional<Client> getById(Long id);
}

