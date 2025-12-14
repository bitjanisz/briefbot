package com.admeliora.briefbot.user.api.dto;

import com.admeliora.briefbot.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(Long id, String givenName, String familyName, String email, String picture, LocalDateTime lastLogin) {

    public static UserDto from(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .givenName(user.getGivenName())
                .familyName(user.getFamilyName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .lastLogin(user.getLastLogin())
                .build();
    }
}