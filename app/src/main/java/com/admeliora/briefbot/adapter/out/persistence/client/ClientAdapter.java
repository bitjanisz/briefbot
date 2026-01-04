package com.admeliora.briefbot.adapter.out.persistence.client;

import com.admeliora.briefbot.adapter.out.persistence.client.jpa.ClientRepositoryJpa;
import com.admeliora.briefbot.application.client.model.Client;
import com.admeliora.briefbot.application.client.port.out.ClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientAdapter implements ClientPort {

    private final ClientRepositoryJpa clientRepository;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return clientRepository.existsById(id);
    }

    @Override
    public Client getReferenceById(Long id) {
        return clientRepository.getReferenceById(id);
    }

    @Override
    public void delete(Client client) {
        clientRepository.delete(client);
    }
}

