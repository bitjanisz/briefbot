package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.model.Account;
import java.util.List;

public interface ListAccountsUseCase {
    List<Account> execute();
}

