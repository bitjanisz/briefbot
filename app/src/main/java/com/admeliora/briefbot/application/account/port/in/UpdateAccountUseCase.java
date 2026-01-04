package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.port.in.command.UpdateAccountCommand;

public interface UpdateAccountUseCase {
    Account execute(UpdateAccountCommand command);
}

