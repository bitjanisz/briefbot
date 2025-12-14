package com.admeliora.briefbot.account.api.dto;

public record CreateAccountDto(
        String name,
        Long ownerId
) {}
