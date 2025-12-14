package com.admeliora.briefbot.user.usecase;

import com.admeliora.briefbot.user.api.dto.UserDto;
import com.admeliora.briefbot.user.port.UserPort;
import com.admeliora.briefbot.user.port.in.ListUsersInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUsersUseCase implements ListUsersInPort {

    private final UserPort userPort;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> execute() {
        return userPort.findAll().stream().map(UserDto::from).toList();
    }
}

