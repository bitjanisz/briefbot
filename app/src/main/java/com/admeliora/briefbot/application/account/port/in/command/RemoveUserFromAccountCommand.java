package com.admeliora.briefbot.application.account.port.in.command;

public record RemoveUserFromAccountCommand(Long accountId, Long userId) {
}

