package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.SwitchAccountPort;
import com.admeliora.briefbot.application.user.port.in.command.SwitchAccountCommand;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import com.admeliora.briefbot.infrastructure.context.AccountFilterContext;
import com.admeliora.briefbot.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for switching user's current account
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SwitchAccountService implements SwitchAccountPort {

    private final UserAccountPort userAccountPort;
    private final UserPort userPort;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountFilterContext context;

    @Override
    @Transactional(readOnly = true)
    public User switchAccount(SwitchAccountCommand command) {

        var userId = context.getUserId();
        // Validate that user has access to the account
        if (!userAccountPort.existsByUserIdAndAccountId(userId, command.accountId())) {
            throw new IllegalArgumentException("User does not have access to account " + command.accountId());
        }

        // Get user details
        User user = userPort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate new JWT with the new accountId
        String token = jwtTokenProvider.createToken(
                user.getEmail(),
                user.getId(),
                command.accountId(),
                user.getGivenName(),
                user.getFamilyName(),
                "form" // or determine auth method
        );

        log.info("User {} switched to account {}", userId, command.accountId());
        return user;
    }
}
