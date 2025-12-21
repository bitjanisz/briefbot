package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import com.admeliora.briefbot.application.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.model.AccountRole;
import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.account.model.UserAccountId;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        AccountRole effectiveRole = command != null ? role : AccountRole.MEMBER;

        if (!userPort.existsById(userId)) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
        if (!accountPort.existsById(accountId)) {
            throw new EntityNotFoundException("Account not found: " + accountId);
        }

        UserAccountId id = new UserAccountId(userId, accountId);

        userAccountPort.findById(id)
                .map(existing -> {
                    existing.setRole(effectiveRole);
                    return userAccountPort.save(existing);
                })
                .orElseGet(() -> {
                    UserAccount link = new UserAccount();
                    link.setId(id);
                    link.setUser(userPort.getReferenceById(userId));
                    link.setAccount(accountPort.getReferenceById(accountId));
                    link.setRole(effectiveRole);
                    return userAccountPort.save(link);
                });
        return accountPort.findById(accountId)
                .orElseThrow();
    }
}
