package com.admeliora.briefbot.user.api;

import com.admeliora.briefbot.user.api.doc.GetAllUsersOperation;
import com.admeliora.briefbot.user.api.dto.UserDto;
import com.admeliora.briefbot.user.domain.User;
import com.admeliora.briefbot.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @GetAllUsersOperation
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }
}