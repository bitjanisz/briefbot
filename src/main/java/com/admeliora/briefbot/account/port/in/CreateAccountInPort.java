package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.account.api.dto.AccountDto;

public interface CreateAccountInPort {
    AccountDto execute(String name, Long ownerId);
}
