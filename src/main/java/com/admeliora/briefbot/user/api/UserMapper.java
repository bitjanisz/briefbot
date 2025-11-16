package com.admeliora.briefbot.user.api;

import com.admeliora.briefbot.user.api.dto.UserDto;
import com.admeliora.briefbot.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}