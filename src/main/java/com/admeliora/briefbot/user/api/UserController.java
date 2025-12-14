package com.admeliora.briefbot.user.api;

import com.admeliora.briefbot.user.api.dto.UserDto;
import com.admeliora.briefbot.user.api.dto.UserUpdateDto;
import com.admeliora.briefbot.user.port.in.ListUsersInPort;
import com.admeliora.briefbot.user.port.in.GetLoggedUserInPort;
import com.admeliora.briefbot.user.port.in.UpdateOwnUserInPort;
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
public class UserController {

    private final ListUsersInPort listUsersInPort;
    private final GetLoggedUserInPort getLoggedUserInPort;
    private final UpdateOwnUserInPort updateOwnUserInPort;

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all registered users.",
            operationId = "getAllUsers"
    )
    public List<UserDto> getAllUsers() {
        return listUsersInPort.execute();
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get logged-in user",
            description = "Retrieves information about the currently authenticated user.",
            operationId = "getLoggedInUser"
    )
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal OidcUser user) {
        return getLoggedUserInPort.execute(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/me")
    @Operation(
            summary = "Update logged-in user",
            description = "Updates information for the currently authenticated user.",
            operationId = "updateLoggedInUser"
    )
    public ResponseEntity<UserDto> updateMe(
            @AuthenticationPrincipal OidcUser principal,
            @RequestBody @Validated UserUpdateDto dto) {

        return updateOwnUserInPort.execute(principal, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}