package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.domain.account.Account;
import com.admeliora.briefbot.account.port.in.command.RemoveUserFromAccountCommand;

public interface RemoveUserFromAccountInPort {
    Account execute(RemoveUserFromAccountCommand command);
}
