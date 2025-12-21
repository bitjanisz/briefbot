package com.admeliora.briefbot.application.account.port.in.command;

import com.admeliora.briefbot.application.account.model.AccountRole;

public record AddUserToAccountCommand(Long accountId, Long userId, AccountRole role) {
}

