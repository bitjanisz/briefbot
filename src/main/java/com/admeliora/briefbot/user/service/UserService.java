package com.admeliora.briefbot.user.service;

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
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getLoggedUser(OidcUser oidcUser) {
        return userRepository.findById(oidcUser.getAttribute("sub"));
    }
}