package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.domain.user.User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public interface GetLoggedUserInPort {
    Optional<User> execute(OidcUser principal);
}
