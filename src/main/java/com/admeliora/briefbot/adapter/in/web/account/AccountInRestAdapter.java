package com.admeliora.briefbot.adapter.in.web.account;

// Adapter wejściowy (REST) – wywołuje porty wejściowe (use case) zgodnie z architekturą heksagonalną

import com.admeliora.briefbot.account.port.in.AddUserToAccountInPort;
import com.admeliora.briefbot.account.port.in.CreateAccountInPort;
import com.admeliora.briefbot.account.port.in.ListAccountsInPort;
import com.admeliora.briefbot.account.port.in.RemoveUserFromAccountInPort;
import com.admeliora.briefbot.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.account.port.in.command.CreateAccountCommand;
import com.admeliora.briefbot.account.port.in.command.RemoveUserFromAccountCommand;
import com.admeliora.briefbot.adapter.in.web.account.dto.AccountDto;
import com.admeliora.briefbot.adapter.in.web.account.dto.AccountSummary;
import com.admeliora.briefbot.adapter.in.web.account.dto.AddUserToAccountDto;
import com.admeliora.briefbot.adapter.in.web.account.dto.CreateAccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Accounts API (adapters)")
public class AccountInRestAdapter {

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
        var command = new CreateAccountCommand(request.name(), request.ownerId());
        var account = createAccountInPort.execute(command);
        return AccountDto.from(account);
    }

    @PutMapping("/{accountId}/users")
    @Operation(summary = "Add or update user on account")
    public AccountDto addUserToAccount(@PathVariable Long accountId,
                                       @Valid @RequestBody AddUserToAccountDto request) {
        var command = new AddUserToAccountCommand(accountId, request.userId(), request.role());
        var account = addUserToAccountInPort.execute(command);
        return AccountDto.from(account);
    }

    @DeleteMapping("/{accountId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove user from account")
    public AccountDto removeUserFromAccount(@PathVariable Long accountId, @PathVariable Long userId) {
        var command = new RemoveUserFromAccountCommand(accountId, userId);
        var account = removeUserFromAccountInPort.execute(command);
        return AccountDto.from(account);
    }
}
