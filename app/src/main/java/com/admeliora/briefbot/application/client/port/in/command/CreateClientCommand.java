package com.admeliora.briefbot.application.client.port.in.command;

public record CreateClientCommand(
        String name,
        String email,
        String companyName,
        String industry
) {
}

