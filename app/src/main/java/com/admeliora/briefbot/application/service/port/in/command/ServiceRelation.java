package com.admeliora.briefbot.application.service.port.in.command;

public record ServiceRelation(
        Long relatedServiceId,
        String relationType,
        String impactDescription
) {
}

