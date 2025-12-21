package com.admeliora.briefbot.application.client.port.in.command;

public record UpdateClientCommand(
        Long id,
        String name,
        String email,
        String companyName,
        String industry
) {
}

