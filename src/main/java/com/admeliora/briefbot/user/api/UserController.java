package com.admeliora.briefbot.user.api;

import com.admeliora.briefbot.user.api.dto.UserDto;
import com.admeliora.briefbot.user.api.dto.UserUpdateDto;
import com.admeliora.briefbot.user.domain.User;
import com.admeliora.briefbot.user.service.UserService;
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

    private final UserService userService;

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all registered users.",
            operationId = "getAllUsers"
    )
    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(UserDto::from)
                .toList();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal OidcUser user) {
        return userService.getLoggedUser(user)
                .map(UserDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateMe(
            @AuthenticationPrincipal OidcUser principal,
            @RequestBody @Validated UserUpdateDto dto) {

        return userService.updateOwnAccount(principal, dto)
                .map(UserDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}