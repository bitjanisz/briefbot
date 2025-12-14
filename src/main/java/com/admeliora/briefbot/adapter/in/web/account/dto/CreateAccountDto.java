package com.admeliora.briefbot.adapter.in.web.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccountDto(
    @NotBlank
    @Size(max = 100)
    String name,

    @NotNull
    Long ownerId
) {}
