package com.khodecamp.online.shop.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "CreateUserDto", description = "Create User Data Transfer Object")
public class CreateUserDto {

    @NotNull
    @NotBlank
    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @NotBlank
    @NotNull
    @Schema(description = "Password of the user", example = "password")
    private String password;

    @Email
    @NotBlank
    @NotNull
    @Schema(description = "Email of the user", example = "jonhdeo@gmail.com")
    private String email;
}
