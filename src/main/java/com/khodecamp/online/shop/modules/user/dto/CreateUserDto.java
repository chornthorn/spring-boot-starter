package com.khodecamp.online.shop.modules.user.dto;

import com.khodecamp.online.shop.core.annotation.IsString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotNull
    private String username;

//    @IsString
    @NotBlank
    private String password;

    @Email
    private String email;
}
