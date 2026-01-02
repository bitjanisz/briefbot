package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.port.in.command.DeleteUserCommand;

/**
 * Use case for deleting a user by admin
 */
public interface DeleteUserPort {

    /**
     * Delete a user
     *
     * @param command delete data
     */
    void delete(DeleteUserCommand command);
}
