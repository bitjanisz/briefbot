package com.admeliora.briefbot.adapter.in.web.service.mapper;

import com.admeliora.briefbot.adapter.in.web.service.model.response.ServiceResponse;
import com.admeliora.briefbot.application.service.model.Service;

public class ServiceMapper {
    public static ServiceResponse toResponse(Service service) {
        if (service == null) return null;
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getAccountId(),
                service.getCreatedAt(),
                service.getUpdatedAt()
        );
    }
}

