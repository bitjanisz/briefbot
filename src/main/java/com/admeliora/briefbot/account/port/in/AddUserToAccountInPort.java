package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.account.model.Account;

public interface AddUserToAccountInPort {
    Account execute(AddUserToAccountCommand command);
}
