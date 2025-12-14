package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.adapter.in.web.user.dto.UserDto;

import java.util.List;

public interface ListUsersInPort {
    List<UserDto> execute();
}

