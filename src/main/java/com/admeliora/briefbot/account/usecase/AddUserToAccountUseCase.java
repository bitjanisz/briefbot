package com.admeliora.briefbot.account.usecase;

import com.admeliora.briefbot.account.port.AccountPort;
import com.admeliora.briefbot.account.port.UserAccountPort;
import com.admeliora.briefbot.account.port.in.AddUserToAccountInPort;
import com.admeliora.briefbot.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.account.model.Account;
import com.admeliora.briefbot.account.model.AccountRole;
import com.admeliora.briefbot.account.model.UserAccount;
import com.admeliora.briefbot.account.model.UserAccountId;
import com.admeliora.briefbot.user.port.UserPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddUserToAccountUseCase implements AddUserToAccountInPort {

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
