package com.admeliora.briefbot.account.port.in.command;

import com.admeliora.briefbot.domain.account.AccountRole;

public record AddUserToAccountCommand(Long accountId, Long userId, AccountRole role) {}

