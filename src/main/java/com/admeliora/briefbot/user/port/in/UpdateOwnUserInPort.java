package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.adapter.in.web.user.dto.UserDto;
import com.admeliora.briefbot.adapter.in.web.user.dto.UserUpdateDto;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public interface UpdateOwnUserInPort {
    Optional<UserDto> execute(OidcUser principal, UserUpdateDto dto);
}

