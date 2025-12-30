package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.port.in.command.CreateAccountCommand;
import com.admeliora.briefbot.application.account.model.Account;

public interface CreateAccountUseCase {
    Account execute(CreateAccountCommand command);
}

