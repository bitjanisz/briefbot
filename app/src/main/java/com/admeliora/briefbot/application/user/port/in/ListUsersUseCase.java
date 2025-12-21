package com.admeliora.briefbot.application.user.port.in;

import com.admeliora.briefbot.application.user.model.User;
import java.util.List;

public interface ListUsersUseCase {
    List<User> execute();
}

