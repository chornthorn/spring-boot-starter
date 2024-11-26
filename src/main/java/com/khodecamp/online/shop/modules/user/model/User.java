package com.khodecamp.online.shop.modules.user.model;

import com.khodecamp.online.shop.modules.auth.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Role> roles = new HashSet<>();
}
