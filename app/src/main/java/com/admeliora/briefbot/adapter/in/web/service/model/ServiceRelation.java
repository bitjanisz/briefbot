package com.admeliora.briefbot.adapter.in.web.service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceRelation(
    @NotNull Long relatedServiceId,
    @NotBlank String relationType,
    String impactDescription
) {}

