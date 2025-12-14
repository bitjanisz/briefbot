package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.account.api.dto.AccountDto;

public interface RemoveUserFromAccountInPort {
    AccountDto execute(Long accountId, Long userId);
}

