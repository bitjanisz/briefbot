package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.port.in.command.RemoveUserFromAccountCommand;
import com.admeliora.briefbot.application.account.model.Account;

public interface RemoveUserFromAccountUseCase {
    Account execute(RemoveUserFromAccountCommand command);
}

