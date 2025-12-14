package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.user.api.dto.UserDto;

import java.util.List;

public interface ListUsersInPort {
    List<UserDto> execute();
}

