package com.admeliora.briefbot.adapter.in.web.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceCreateDto(
    @NotBlank
    @Size(max = 255)
    String name,
    @Size(max = 10000)
    String description,
    @NotNull
    Long accountId
) {}
