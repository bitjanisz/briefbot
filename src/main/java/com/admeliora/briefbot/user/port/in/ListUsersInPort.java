package com.admeliora.briefbot.user.port.in;

import com.admeliora.briefbot.adapter.in.web.user.response.UserResponse;

import java.util.List;

public interface ListUsersInPort {
    List<UserResponse> execute();
}
