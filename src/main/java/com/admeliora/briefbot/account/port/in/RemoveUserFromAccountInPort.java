package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.account.port.in.command.RemoveUserFromAccountCommand;
import com.admeliora.briefbot.domain.account.Account;

public interface RemoveUserFromAccountInPort {
    Account execute(RemoveUserFromAccountCommand command);
}
