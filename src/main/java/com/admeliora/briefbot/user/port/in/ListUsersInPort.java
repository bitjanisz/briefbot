package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.user.model.User;

import java.util.List;

public interface ListUsersInPort {
    List<User> execute();
}
