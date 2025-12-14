package com.admeliora.briefbot.adapter.in.web.user.mapper;

import com.admeliora.briefbot.adapter.in.web.user.response.UserResponse;
import com.admeliora.briefbot.domain.user.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .givenName(user.getGivenName())
                .familyName(user.getFamilyName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .lastLogin(user.getLastLogin())
                .build();
    }
}

