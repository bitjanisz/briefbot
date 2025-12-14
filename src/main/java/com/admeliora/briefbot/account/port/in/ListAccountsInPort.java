package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.domain.account.Account;

import java.util.List;

public interface ListAccountsInPort {
    List<Account> execute();
}

