package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.model.UserAccountDetails;

import java.util.List;

public interface ListUsersInAccountUseCase {
    List<UserAccountDetails> execute(Long accountId);
}
