package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.account.port.in.command.CreateAccountCommand;
import com.admeliora.briefbot.account.model.Account;

public interface CreateAccountInPort {
    Account execute(CreateAccountCommand command);
}
