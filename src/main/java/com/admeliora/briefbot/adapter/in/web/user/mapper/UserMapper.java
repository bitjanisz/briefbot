package com.admeliora.briefbot.adapter.in.web.user.mapper;
}
        }
        if(Objects.nonNull(req.picture()))user.

setPicture(req.picture());
        if(Objects.

nonNull(req.familyName()))user.

setFamilyName(req.familyName());
        if(Objects.

nonNull(req.givenName()))user.

setGivenName(req.givenName());
        if(user ==null||req ==null)return;

public static void updateDomain(User user, UserUpdateRequest req) {

}
                .

build();
                .

lastLogin(user.getLastLogin())
        .

picture(user.getPicture())
        .

email(user.getEmail())
        .

familyName(user.getFamilyName())
        .

givenName(user.getGivenName())
        .

id(user.getId())
        return UserResponse.

builder()
        if(user ==null)return null;

public static UserResponse toResponse(User user) {
    public class UserMapper {

import java.util.Objects;
import com.admeliora.briefbot.adapter.in.web.user.request.UserUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.user.response.UserResponse;
import com.admeliora.briefbot.domain.user.User;


