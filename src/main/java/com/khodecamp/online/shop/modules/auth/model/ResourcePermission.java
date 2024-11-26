package com.khodecamp.online.shop.modules.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResourcePermission implements Serializable {
    private String resource;
    private String scope;
}

