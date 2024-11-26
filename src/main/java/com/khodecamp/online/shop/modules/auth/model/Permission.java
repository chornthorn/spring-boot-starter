package com.khodecamp.online.shop.modules.auth.model;

import lombok.Data;

@Data
public class Permission {
    private Long id;
    private String name;
    private Role role;
    private Resource resource;
    private Scope scope;
}

