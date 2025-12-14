package com.admeliora.briefbot.service.usecase;

import com.admeliora.briefbot.domain.service.Service;
import com.admeliora.briefbot.service.port.in.UpdateServiceInPort;
import com.admeliora.briefbot.service.port.out.ServicePort;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Tag(name = "Services", description = "CRUD for services")
public class UpdateServiceUseCase implements UpdateServiceInPort {
    private final ServicePort servicePort;

    @Override
    @Operation(summary = "Update service", description = "Updates an existing service by id. Returns the updated service domain object.")
    public Service update(Long id, String name, String description) {
        Service service = servicePort.findById(id).orElseThrow();
        service.setName(name);
        service.setDescription(description);
        service.setUpdatedAt(Instant.now());
        return servicePort.save(service);
    }
}
