package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.application.account.model.Account;

public interface AddUserToAccountUseCase {
    Account execute(AddUserToAccountCommand command);
}

