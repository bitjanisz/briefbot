package com.admeliora.briefbot.adapter.in.web.service.mapper;

import com.admeliora.briefbot.adapter.in.web.service.model.response.ServiceResponse;
import com.admeliora.briefbot.application.service.model.Service;
import com.admeliora.briefbot.application.service.model.ServiceRelation;

import java.util.List;

public class ServiceMapper {
    public static ServiceResponse toResponse(Service service) {
        if (service == null) return null;
//        List<com.admeliora.briefbot.adapter.in.web.service.model.ServiceRelation> serviceRelations =
//                service.getServiceRelations() != null ? service.getServiceRelations().stream()
//                        .map(ServiceMapper::toRelationResponse)
//                        .toList() : List.of();
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getAccountId(),
                service.getBasePrice(),
                service.getVatRate(),
                service.getCurrency(),
                service.getPricingUnit(),
                service.getMinPriceThreshold(),
                service.getIsActive(),
                service.getCreatedAt(),
                service.getUpdatedAt(),
                service.getVersion()
//                serviceRelations
        );
    }

    private static com.admeliora.briefbot.adapter.in.web.service.model.ServiceRelation toRelationResponse(ServiceRelation relation) {
        return new com.admeliora.briefbot.adapter.in.web.service.model.ServiceRelation(
                relation.getRelatedServiceId(),
                relation.getRelationType(),
                relation.getImpactDescription()
        );
    }
}
