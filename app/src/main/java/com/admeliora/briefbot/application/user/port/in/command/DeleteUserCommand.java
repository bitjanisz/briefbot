package com.admeliora.briefbot.application.user.port.in.command;

/**
 * Command for deleting a user by admin
 */
public record DeleteUserCommand(
        Long id
) {
}
