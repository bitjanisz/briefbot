package com.admeliora.briefbot.account.port.in;

import com.admeliora.briefbot.account.api.dto.AccountDto;
import com.admeliora.briefbot.domain.account.AccountRole;

public interface AddUserToAccountInPort {
    AccountDto execute(Long accountId, Long userId, AccountRole role);
}
