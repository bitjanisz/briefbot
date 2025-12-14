package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.account.model.Account;
import java.util.List;

public interface ListAccountsPort {
    List<Account> execute();
}

