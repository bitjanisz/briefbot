package com.admeliora.briefbot.service.usecase;

import com.admeliora.briefbot.domain.service.Service;
import com.admeliora.briefbot.service.port.in.CreateServiceInPort;
import com.admeliora.briefbot.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Tag(name = "Services", description = "CRUD for services")
public class CreateServiceUseCase implements CreateServiceInPort {
    private final ServicePort servicePort;

    @Override
    @Operation(summary = "Create service", description = "Creates a new service and returns the domain object.")
    public Service create(String name, String description, Long accountId) {
        Service service = new Service();
        service.setName(name);
        service.setDescription(description);
        service.setAccountId(accountId);
        service.setCreatedAt(Instant.now());
        service.setUpdatedAt(Instant.now());
        return servicePort.save(service);
    }
}
