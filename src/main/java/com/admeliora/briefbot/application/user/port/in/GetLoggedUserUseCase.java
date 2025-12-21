package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import java.util.Optional;

public interface GetLoggedUserUseCase {
    Optional<User> execute(OidcUser principal);
}

