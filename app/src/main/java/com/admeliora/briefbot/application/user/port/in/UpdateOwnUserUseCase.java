package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.command.UpdateOwnUserCommand;

import java.util.Optional;

public interface UpdateOwnUserUseCase {
    Optional<User> execute(UpdateOwnUserCommand command);
}

