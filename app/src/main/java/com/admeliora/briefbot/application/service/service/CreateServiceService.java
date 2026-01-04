package com.admeliora.briefbot.application.service.service;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.common.exception.NoAccountAssignedException;
import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.model.ServiceRelation;
import com.admeliora.briefbot.application.service.port.in.CreateServiceUseCase;
import com.admeliora.briefbot.application.service.port.in.command.CreateServiceCommand;
import com.admeliora.briefbot.application.service.port.out.ServicePort;
import com.admeliora.briefbot.infrastructure.context.AccountFilterContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Tag(name = "Services", description = "CRUD for services")
public class CreateServiceService implements CreateServiceUseCase {
    private final ServicePort servicePort;
    private final AccountPort accountPort;
    private final AccountFilterContext accountFilterContext;

    @Override
    @Operation(summary = "Create service", description = "Creates a new service and returns the domain object.")
    public Service create(CreateServiceCommand command) {
        if (accountFilterContext.getAccountId() == null) {
            throw new NoAccountAssignedException(accountFilterContext.getUserEmail());
        }
        Service service = Service.builder()
                .name(command.name())
                .description(command.description())
                .accountId(accountFilterContext.getAccountId())
                .basePrice(command.basePrice())
                .vatRate(command.vatRate())
                .currency(command.currency())
                .pricingUnit(command.pricingUnit())
                .minPriceThreshold(command.minPriceThreshold())
                .isActive(command.isActive())
                .build();

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
