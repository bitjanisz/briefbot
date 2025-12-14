package com.admeliora.briefbot.user.service;

import com.admeliora.briefbot.user.api.dto.UserDto;
import com.admeliora.briefbot.user.api.dto.UserUpdateDto;
import com.admeliora.briefbot.user.domain.User;
import com.admeliora.briefbot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::from).toList();
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> getLoggedUser(OidcUser oidcUser) {
        return Optional.ofNullable(oidcUser)
                .map(u -> (String) u.getAttribute("sub"))
                .flatMap(userRepository::findByOidcSub)
                .map(UserDto::from);
    }

    @Transactional
    public Optional<UserDto> updateOwnAccount(OidcUser principal, UserUpdateDto dto) {
        if (principal == null) return Optional.empty();
        String sub = principal.getAttribute("sub");
        if (sub == null) return Optional.empty();

        return userRepository.findByOidcSub(sub)
                .map(user -> {
                    if (dto.givenName() != null) user.setGivenName(dto.givenName());
                    if (dto.familyName() != null) user.setFamilyName(dto.familyName());
                    if (dto.picture() != null) user.setPicture(dto.picture());
                    return userRepository.save(user);
                })
                .map(UserDto::from);
    }
}