package com.khodecamp.online.shop.modules.auth.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResourcePermission implements Serializable {
    private String resource;
    private String scope;
}

