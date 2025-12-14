package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.domain.account.Account;
import com.admeliora.briefbot.account.port.in.command.AddUserToAccountCommand;

public interface AddUserToAccountInPort {
    Account execute(AddUserToAccountCommand command);
}
