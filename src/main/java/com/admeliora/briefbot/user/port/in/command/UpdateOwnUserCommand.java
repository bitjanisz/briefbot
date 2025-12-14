package com.admeliora.briefbot.user.port.in.command;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public record UpdateOwnUserCommand(
    OidcUser principal,
    String givenName,
    String familyName,
    String picture
) {}
