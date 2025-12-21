package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import com.admeliora.briefbot.application.account.port.in.command.RemoveUserFromAccountCommand;
import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.model.UserAccountId;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveUserFromAccountService implements com.admeliora.briefbot.application.account.port.in.RemoveUserFromAccountUseCase {

    private final UserAccountPort userAccountPort;
    private final AccountPort accountPort;


    @Override
    @Transactional
    public Account execute(RemoveUserFromAccountCommand command) {
        Long userId = command.userId();
        Long accountId = command.accountId();

        UserAccountId id = new UserAccountId(userId, accountId);
        if (!userAccountPort.existsById(id)) {
            throw new EntityNotFoundException(
                    "UserAccount not found for userId=" + userId + ", accountId=" + accountId
            );
        }
        userAccountPort.deleteById(id);
        return accountPort.findById(accountId).orElseThrow();
    }
}
