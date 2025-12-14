package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.account.model.Account;

import java.util.List;

public interface ListAccountsInPort {
    List<Account> execute();
}

