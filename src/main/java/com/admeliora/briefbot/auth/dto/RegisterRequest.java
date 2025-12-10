package com.admeliora.briefbot.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
public class RegisterRequest {
    @NotBlank
    private String firstName;
    @NotBlank private String lastName;
    @Email
    @NotBlank private String email;
    @NotBlank private String password;
}
