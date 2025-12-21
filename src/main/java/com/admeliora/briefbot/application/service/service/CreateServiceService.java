package com.admeliora.briefbot.application.service.service;

import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.port.in.command.CreateServiceCommand;
import com.admeliora.briefbot.application.service.port.out.ServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Tag(name = "Services", description = "CRUD for services")
public class CreateServiceService implements com.admeliora.briefbot.application.service.port.in.CreateServiceUseCase {
    private final ServicePort servicePort;

    @Override
    @Operation(summary = "Create service", description = "Creates a new service and returns the domain object.")
    public Service create(CreateServiceCommand command) {
        Service service = new Service();
        service.setName(command.name());
        service.setDescription(command.description());
        service.setAccountId(command.accountId());
        service.setCreatedAt(Instant.now());
        service.setUpdatedAt(Instant.now());
        return servicePort.save(service);
    }
}
