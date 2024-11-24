package com.khodecamp.online.shop.modules.todo.model;

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
