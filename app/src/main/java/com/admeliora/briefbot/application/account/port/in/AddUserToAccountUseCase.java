package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.port.in.command.AddUserToAccountCommand;

public interface AddUserToAccountUseCase {
    Account execute(AddUserToAccountCommand command);
}

