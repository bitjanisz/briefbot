package com.admeliora.briefbot.application.account.port.in.command;

public record UpdateAccountCommand(
        Long id,
        String name
) {
}

