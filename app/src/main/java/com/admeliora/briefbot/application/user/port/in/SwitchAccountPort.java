package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.command.SwitchAccountCommand;

/**
 * Use case for switching user's current account
 */
public interface SwitchAccountPort {

    /**
     * Switch the user's current account context and return new JWT
     *
     * @param command switch account command
     * @return new JWT token with switched account
     */
    User switchAccount(SwitchAccountCommand command);
}
