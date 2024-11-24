package com.khodecamp.online.shop.modules.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String createdAt;
    private String updatedAt;
    private String identity;
}
