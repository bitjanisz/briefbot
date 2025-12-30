package com.admeliora.briefbot.application.service.service;

import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.model.ServiceRelation;
import com.admeliora.briefbot.application.service.port.in.UpdateServiceUseCase;
import com.admeliora.briefbot.application.service.port.in.command.UpdateServiceCommand;
import com.admeliora.briefbot.application.service.port.out.ServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Tag(name = "Services", description = "CRUD for services")
public class UpdateServiceService implements UpdateServiceUseCase {
    private final ServicePort servicePort;

    @Override
    @Operation(summary = "Update service", description = "Updates an existing service by id. Returns the updated service domain object.")
    public Service update(UpdateServiceCommand command) {
        Service service = servicePort.findById(command.id()).orElseThrow();
        service.setName(command.name());
        service.setDescription(command.description());
        service.setBasePrice(command.basePrice());
        service.setVatRate(command.vatRate());
        service.setCurrency(command.currency());
        service.setPricingUnit(command.pricingUnit());
        service.setMinPriceThreshold(command.minPriceThreshold());
        service.setIsActive(command.isActive());

        service.getServiceRelations().clear();

        if (command.relations() != null && !command.relations().isEmpty()) {
            command.relations().forEach(relationCommand -> {
                var relatedService = servicePort.findById(relationCommand.relatedServiceId()).orElseThrow();
                ServiceRelation serviceRelation = ServiceRelation.builder()
                    .parentService(service)
                    .relatedServiceId(relatedService.getId())
                    .relationType(relationCommand.relationType())
                    .impactDescription(relationCommand.impactDescription())
                    .build();
                service.getServiceRelations().add(serviceRelation);
            });
        }

        return servicePort.save(service);
    }
}
