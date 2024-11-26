package com.khodecamp.online.shop.modules.auth.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @NotNull
    private String username;

    @NotBlank(message = "Password is required")
    @NotNull
    @Min(6)
    private String password;
}
