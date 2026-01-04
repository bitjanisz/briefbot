package com.admeliora.briefbot.application.client.port.in;

import com.admeliora.briefbot.application.client.model.Client;

import java.util.List;

public interface ListClientsPort {
    List<Client> listByAccountId(Long accountId);
}

