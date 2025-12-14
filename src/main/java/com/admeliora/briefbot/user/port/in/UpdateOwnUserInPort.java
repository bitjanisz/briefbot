package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.adapter.in.web.user.request.UserUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.user.response.UserResponse;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public interface UpdateOwnUserInPort {
    Optional<UserResponse> execute(OidcUser principal, UserUpdateRequest dto);
}
