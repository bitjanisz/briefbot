package com.admeliora.briefbot.application.user.service;

import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUsersService implements com.admeliora.briefbot.application.user.port.in.ListUsersUseCase {

    private final UserPort userPort;

    @Override
    @Transactional(readOnly = true)
    public List<User> execute() {
        return userPort.findAll();
    }
}

