package com.admeliora.briefbot.application.service.service;

import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.port.in.command.UpdateServiceCommand;
import com.admeliora.briefbot.application.service.port.out.ServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Tag(name = "Services", description = "CRUD for services")
public class UpdateServiceService implements com.admeliora.briefbot.application.service.port.in.UpdateServiceUseCase {
    private final ServicePort servicePort;

    @Override
    @Operation(summary = "Update service", description = "Updates an existing service by id. Returns the updated service domain object.")
    public Service update(UpdateServiceCommand command) {
        Service service = servicePort.findById(command.id()).orElseThrow();
        service.setName(command.name());
        service.setDescription(command.description());
        service.setUpdatedAt(Instant.now());
        return servicePort.save(service);
    }
}
