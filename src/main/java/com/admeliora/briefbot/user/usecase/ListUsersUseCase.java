package com.admeliora.briefbot.user.usecase;

import com.admeliora.briefbot.domain.user.User;
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
    public List<User> execute() {
        return userPort.findAll();
    }
}

