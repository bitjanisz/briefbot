package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.command.CreateUserCommand;

/**
 * Use case for creating a user by admin
 */
public interface CreateUserPort {

    /**
     * Create a new user
     *
     * @param command creation data
     * @return created user
     */
    User create(CreateUserCommand command);
}
