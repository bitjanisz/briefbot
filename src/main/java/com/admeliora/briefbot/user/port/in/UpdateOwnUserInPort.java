package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.domain.user.User;
import com.admeliora.briefbot.user.port.in.command.UpdateOwnUserCommand;

import java.util.Optional;

public interface UpdateOwnUserInPort {
    Optional<User> execute(UpdateOwnUserCommand command);
}
