package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.port.in.command.RemoveUserFromAccountCommand;

public interface RemoveUserFromAccountUseCase {
    Account execute(RemoveUserFromAccountCommand command);
}

