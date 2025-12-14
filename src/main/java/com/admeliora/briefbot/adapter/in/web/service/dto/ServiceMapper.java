package com.admeliora.briefbot.adapter.in.web.service.dto;

import com.admeliora.briefbot.domain.service.Service;

public class ServiceMapper {
    public static ServiceDto toDto(Service service) {
        if (service == null) return null;
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

