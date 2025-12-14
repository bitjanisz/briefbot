package com.admeliora.briefbot.adapter.in.web.service.dto;

import com.admeliora.briefbot.domain.service.Service;

import java.time.Instant;

public record ServiceDto(Long id, String name, String description, Long accountId, Instant createdAt, Instant updatedAt) {
    public static ServiceDto from(Service service) {
        return new ServiceDto(
            service.getId(),
            service.getName(),
            service.getDescription(),
            service.getAccountId(),
            service.getCreatedAt(),
            service.getUpdatedAt()
        );
    }
}

