package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.domain.user.User;

import java.util.List;

public interface ListUsersInPort {
    List<User> execute();
}
