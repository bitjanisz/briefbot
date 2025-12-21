package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.command.RegisterUserCommand;

/**
 * Use case for registering a new user with email and password
 */
public interface RegisterUserPort {

    /**
     * Register a new user and send temporary password via email
     *
     * @param command registration data
     * @return created user
     */
    User register(RegisterUserCommand command);
}

