package com.khodecamp.online.shop.modules.todo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Todo Model")
public class Todo {
    @Schema(description = "Todo ID", example = "1")
    private Long id;

    @Schema(description = "Todo Name", example = "Buy Milk")
    private String name;

    @Schema(description = "Todo Description", example = "Buy 1 liter of milk")
    private String description;

    @Schema(description = "Todo is Completed", example = "false")
    private Boolean isCompleted;

    @Schema(description = "Todo Created At", example = "2021-08-01T00:00:00", hidden = true)
    private LocalDateTime createdAt;

    @Schema(description = "Todo Updated At", example = "2021-08-01T00:00:00", hidden = true)
    private LocalDateTime updatedAt;
}
