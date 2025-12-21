package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import com.admeliora.briefbot.application.user.port.in.command.UpdateOwnUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateOwnUserService implements com.admeliora.briefbot.application.user.port.in.UpdateOwnUserUseCase {

    private final UserPort userPort;

    @Override
    @Transactional
    public Optional<User> execute(UpdateOwnUserCommand command) {
        if (command == null || command.principal() == null) return Optional.empty();
        String sub = command.principal().getAttribute("sub");
        if (sub == null) return Optional.empty();
        return userPort.findByOidcSub(sub)
                .map(user -> {
                    if (command.givenName() != null) user.setGivenName(command.givenName());
                    if (command.familyName() != null) user.setFamilyName(command.familyName());
                    if (command.picture() != null) user.setPicture(command.picture());
                    return userPort.save(user);
                });
    }
}
