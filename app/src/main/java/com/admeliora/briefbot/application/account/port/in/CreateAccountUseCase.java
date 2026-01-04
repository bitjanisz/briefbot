package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.port.in.command.CreateAccountCommand;

public interface CreateAccountUseCase {
    Account execute(CreateAccountCommand command);
}

