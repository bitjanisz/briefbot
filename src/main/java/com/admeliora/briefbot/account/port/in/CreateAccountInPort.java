package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.domain.account.Account;
import com.admeliora.briefbot.account.port.in.command.CreateAccountCommand;

public interface CreateAccountInPort {
    Account execute(CreateAccountCommand command);
}
