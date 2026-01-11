package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.model.UserAccountDetails;
import com.admeliora.briefbot.application.account.port.in.command.AddUserToAccountCommand;

public interface AddUserToAccountUseCase {
    UserAccountDetails execute(AddUserToAccountCommand command);
}
