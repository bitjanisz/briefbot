package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.user.model.User;
import com.admeliora.briefbot.user.port.in.command.UpdateOwnUserCommand;
import java.util.Optional;

public interface UpdateOwnUserPort {
    Optional<User> execute(UpdateOwnUserCommand command);
}

