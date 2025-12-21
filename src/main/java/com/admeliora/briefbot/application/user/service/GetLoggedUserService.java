package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetLoggedUserService implements com.admeliora.briefbot.application.user.port.in.GetLoggedUserUseCase {

    private final UserPort userPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> execute(OidcUser principal) {
        if (principal == null) return Optional.empty();
        String sub = principal.getAttribute("sub");
        if (sub == null) return Optional.empty();
        return userPort.findByOidcSub(sub);
    }
}

