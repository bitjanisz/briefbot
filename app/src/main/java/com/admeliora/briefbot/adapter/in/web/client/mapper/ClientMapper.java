package com.admeliora.briefbot.adapter.in.web.client.mapper;

import com.admeliora.briefbot.adapter.in.web.client.model.response.ClientResponse;
import com.admeliora.briefbot.application.client.model.Client;

/**
 * ClientMapper (DDD-aligned)
 * Client no longer holds collections of child entities
 */
public class ClientMapper {
    public static ClientResponse toResponse(Client client) {
        if (client == null) return null;
        return new ClientResponse(
                client.getId(),
                client.getAccountId(),
                client.getName(),
                client.getEmail(),
                client.getCompanyName(),
                client.getIndustry(),
                client.getCreatedAt()
        );
    }
}

