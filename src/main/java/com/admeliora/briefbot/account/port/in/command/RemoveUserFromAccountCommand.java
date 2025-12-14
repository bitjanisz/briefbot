package com.admeliora.briefbot.account.port.in.command;

public record RemoveUserFromAccountCommand(Long accountId, Long userId) {
}

