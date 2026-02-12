package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.command.UpdateUserCommand;

/**
 * Use case for updating a user by admin
 */
public interface UpdateUserPort {

    /**
     * Update an existing user
     *
     * @param command update data
     * @return updated user
     */
    User update(UpdateUserCommand command);
}
