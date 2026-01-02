package com.admeliora.briefbot.application.user.port.in.command;

/**
 * Command for switching user's current account
 */
public record SwitchAccountCommand(
        Long accountId
) {
}
