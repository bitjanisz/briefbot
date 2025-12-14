package com.admeliora.briefbot.user.usecase;

import com.admeliora.briefbot.user.model.User;
import com.admeliora.briefbot.user.port.out.UserPort;
import com.admeliora.briefbot.user.port.in.ListUsersPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUsersUseCase implements ListUsersPort {

    private final UserPort userPort;

    @Override
    @Transactional(readOnly = true)
    public List<User> execute() {
        return userPort.findAll();
    }
}

