package com.admeliora.briefbot.user.usecase;

import com.admeliora.briefbot.user.api.dto.UserDto;
import com.admeliora.briefbot.user.api.dto.UserUpdateDto;
import com.admeliora.briefbot.user.port.in.UpdateOwnUserInPort;
import com.admeliora.briefbot.user.port.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateOwnUserUseCase implements UpdateOwnUserInPort {

    private final UserPort userPort;

    @Override
    @Transactional
    public Optional<UserDto> execute(OidcUser principal, UserUpdateDto dto) {
        if (principal == null) return Optional.empty();
        String sub = principal.getAttribute("sub");
        if (sub == null) return Optional.empty();

        return userPort.findByOidcSub(sub)
                .map(user -> {
                    if (dto.givenName() != null) user.setGivenName(dto.givenName());
                    if (dto.familyName() != null) user.setFamilyName(dto.familyName());
                    if (dto.picture() != null) user.setPicture(dto.picture());
                    return userPort.save(user);
                })
                .map(UserDto::from);
    }
}
