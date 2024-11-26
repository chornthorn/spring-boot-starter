package com.khodecamp.online.shop.modules.auth.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Resource {
    private Long id;
    private String name;
    private String description;
    private Set<Scope> scopes = new HashSet<>();
}
