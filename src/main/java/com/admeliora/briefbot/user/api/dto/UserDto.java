package com.admeliora.briefbot.user.api.dto;

import com.admeliora.briefbot.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(String name, String email, String picture, LocalDateTime lastLogin) {

    public static UserDto from(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .lastLogin(user.getLastLogin())
                .build();
    }
}