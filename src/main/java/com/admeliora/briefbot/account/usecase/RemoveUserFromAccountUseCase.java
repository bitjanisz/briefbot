package com.admeliora.briefbot.account.usecase;

import com.admeliora.briefbot.account.port.AccountPort;
import com.admeliora.briefbot.account.port.in.command.RemoveUserFromAccountCommand;
import com.admeliora.briefbot.domain.account.Account;
import com.admeliora.briefbot.domain.account.UserAccountId;
import com.admeliora.briefbot.account.port.UserAccountPort;
import com.admeliora.briefbot.account.port.in.RemoveUserFromAccountInPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveUserFromAccountUseCase implements RemoveUserFromAccountInPort {

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
