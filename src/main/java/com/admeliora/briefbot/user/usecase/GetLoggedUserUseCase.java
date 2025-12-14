package com.admeliora.briefbot.user.usecase;

import com.admeliora.briefbot.domain.user.User;
import com.admeliora.briefbot.user.port.UserPort;
import com.admeliora.briefbot.user.port.in.GetLoggedUserInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetLoggedUserUseCase implements GetLoggedUserInPort {

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

