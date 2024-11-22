package com.khodecamp.online.shop.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Todo {
    private Long id;
    private String name;
    private String description;
    private Boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
