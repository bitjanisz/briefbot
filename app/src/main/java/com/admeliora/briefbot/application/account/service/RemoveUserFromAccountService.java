package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.account.port.in.command.RemoveUserFromAccountCommand;
import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        // Find the UserAccount by userId and accountId
        List<UserAccount> userAccounts = userAccountPort.findByAccountId(accountId);
        UserAccount userAccount = userAccounts.stream()
                .filter(ua -> ua.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "UserAccount not found for userId=" + userId + ", accountId=" + accountId
                ));

        userAccountPort.deleteById(userAccount.getId());
        return accountPort.findById(accountId).orElseThrow();
    }
}
