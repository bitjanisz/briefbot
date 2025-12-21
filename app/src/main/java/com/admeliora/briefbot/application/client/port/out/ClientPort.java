package com.admeliora.briefbot.application.client.port.out;

import com.admeliora.briefbot.application.client.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientPort {
    Client save(Client client);

    Optional<Client> findById(Long id);

    List<Client> findAll();

    boolean existsById(Long id);

    Client getReferenceById(Long id);

    void delete(Client client);
}

