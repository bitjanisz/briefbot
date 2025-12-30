package com.admeliora.briefbot.application.user.port.in.command;

/**
 * Command for updating own user information
 * Works for both OAuth2 and form-based authentication
 */
public record UpdateOwnUserCommand(
    String email, // Email of the logged-in user
    String givenName,
    String familyName,
    String picture
) {}
