package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.command.LoginCommand;

/**
 * Use case for user login with email and password
 */
public interface LoginPort {

    /**
     * Authenticate user with email and password
     *
     * @param command login credentials
     * @return authenticated user
     * @throws IllegalArgumentException if credentials are invalid
     */
    User login(LoginCommand command);
}

