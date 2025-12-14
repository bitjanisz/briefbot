package com.admeliora.briefbot.adapter.in.web.user;

import com.admeliora.briefbot.adapter.in.web.user.mapper.UserMapper;
import com.admeliora.briefbot.adapter.in.web.user.request.UserUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.user.response.UserResponse;
import com.admeliora.briefbot.user.port.in.GetLoggedUserInPort;
import com.admeliora.briefbot.user.port.in.ListUsersInPort;
import com.admeliora.briefbot.user.port.in.UpdateOwnUserInPort;
import com.admeliora.briefbot.user.port.in.command.UpdateOwnUserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management API")
public class UserInRestAdapter {

    private final ListUsersInPort listUsersInPort;
    private final GetLoggedUserInPort getLoggedUserInPort;
    private final UpdateOwnUserInPort updateOwnUserInPort;

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all registered users.",
            operationId = "getAllUsers"
    )
    public List<UserResponse> getAllUsers() {
        return listUsersInPort.execute().stream().map(UserMapper::toResponse).toList();
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get logged-in user",
            description = "Retrieves information about the currently authenticated user.",
            operationId = "getLoggedInUser"
    )
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal OidcUser user) {
        return getLoggedUserInPort.execute(user)
                .map(UserMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/me")
    @Operation(
            summary = "Update logged-in user",
            description = "Updates information for the currently authenticated user.",
            operationId = "updateLoggedInUser"
    )
    public ResponseEntity<UserResponse> updateMe(
            @AuthenticationPrincipal OidcUser principal,
            @RequestBody @Validated UserUpdateRequest updateRequest) {
        var command = new UpdateOwnUserCommand(principal, updateRequest.givenName(), updateRequest.familyName(), updateRequest.picture());
        return updateOwnUserInPort.execute(command)
                .map(UserMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
