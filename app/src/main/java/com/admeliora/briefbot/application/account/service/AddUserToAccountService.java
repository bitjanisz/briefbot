package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.model.AccountRole;
import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddUserToAccountService implements com.admeliora.briefbot.application.account.port.in.AddUserToAccountUseCase {

    private final AccountPort accountPort;
    private final UserPort userPort;
    private final UserAccountPort userAccountPort;

    @Override
    @Transactional
    public Account execute(AddUserToAccountCommand command) {
        Long userId = command.userId();
        Long accountId = command.accountId();
        AccountRole role = command.role();

        AccountRole effectiveRole = role != null ? role : AccountRole.MEMBER;

        if (!userPort.existsById(userId)) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
        if (!accountPort.existsById(accountId)) {
            throw new EntityNotFoundException("Account not found: " + accountId);
        }

        UserAccount link = new UserAccount();
        link.setUserId(userId);
        link.setAccountId(accountId);
        link.setRole(effectiveRole);

        userAccountPort.save(link);

        return accountPort.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + accountId));
    }
}
