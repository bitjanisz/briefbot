package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.user.api.dto.UserDto;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public interface GetLoggedUserInPort {
    Optional<UserDto> execute(OidcUser principal);
}

