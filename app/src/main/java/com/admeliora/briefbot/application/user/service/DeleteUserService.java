package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.user.port.in.DeleteUserPort;
import com.admeliora.briefbot.application.user.port.in.command.DeleteUserCommand;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for deleting users by admin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserPort {

    private final UserPort userPort;

    @Override
    @Transactional
    public void delete(DeleteUserCommand command) {
        // Check if user exists
        if (!userPort.existsById(command.id())) {
            throw new IllegalArgumentException("User with id " + command.id() + " not found");
        }

        userPort.deleteById(command.id());
        log.info("User deleted successfully: {}", command.id());
    }
}
