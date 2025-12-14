package com.admeliora.briefbot.account.api;

import com.admeliora.briefbot.account.api.dto.AccountDto;
import com.admeliora.briefbot.account.api.dto.AddUserToAccountDto;
import com.admeliora.briefbot.account.api.dto.CreateAccountDto;
import com.admeliora.briefbot.account.api.dto.AccountSummary;
import com.admeliora.briefbot.account.port.in.AddUserToAccountInPort;
import com.admeliora.briefbot.account.port.in.CreateAccountInPort;
import com.admeliora.briefbot.account.port.in.ListAccountsInPort;
import com.admeliora.briefbot.account.port.in.RemoveUserFromAccountInPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Accounts API (adapters)")
public class AccountRestController {

    private final ListAccountsInPort listAccountsInPort;
    private final CreateAccountInPort createAccountInPort;
    private final AddUserToAccountInPort addUserToAccountInPort;
    private final RemoveUserFromAccountInPort removeUserFromAccountInPort;

    @GetMapping
    @Operation(summary = "List accounts")
    public List<AccountSummary> listAccounts(@AuthenticationPrincipal OidcUser oidcUser) {
        return listAccountsInPort.execute().stream()
                .map(AccountSummary::from)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create account")
    public AccountDto createAccount(@AuthenticationPrincipal OidcUser oidcUser,
                                 @Valid @RequestBody CreateAccountDto request) {
        return createAccountInPort.execute(request.name(), request.ownerId());
    }

    @PutMapping("/{accountId}/users")
    @Operation(summary = "Add or update user on account")
    public AccountDto addUserToAccount(@PathVariable Long accountId,
                                       @Valid @RequestBody AddUserToAccountDto request) {
        return addUserToAccountInPort.execute(accountId, request.userId(), request.role());
    }

    @DeleteMapping("/{accountId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove user from account")
    public AccountDto removeUserFromAccount(@PathVariable Long accountId, @PathVariable Long userId) {
        return removeUserFromAccountInPort.execute(accountId, userId);
    }
}

