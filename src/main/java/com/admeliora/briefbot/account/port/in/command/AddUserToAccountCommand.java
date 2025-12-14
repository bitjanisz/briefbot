package com.admeliora.briefbot.account.port.in.command;

import com.admeliora.briefbot.account.model.AccountRole;

public record AddUserToAccountCommand(Long accountId, Long userId, AccountRole role) {
}

