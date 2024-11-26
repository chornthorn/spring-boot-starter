package com.khodecamp.online.shop.modules.todo;

import com.khodecamp.online.shop.core.annotation.PageableParam;
import com.khodecamp.online.shop.core.annotation.SearchableParam;
import com.khodecamp.online.shop.core.annotation.SortableParam;
import com.khodecamp.online.shop.core.request.PaginationRequest;
import com.khodecamp.online.shop.core.response.PageResponse;
import com.khodecamp.online.shop.core.request.SortRequest;
import com.khodecamp.online.shop.core.response.ResponseBuilder;
import com.khodecamp.online.shop.core.response.ResponseDto;
import com.khodecamp.online.shop.core.request.SearchRequest;
import com.khodecamp.online.shop.modules.todo.model.Todo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@Slf4j
@Tag(name = "Todo", description = "Todo API")
@PreAuthorize("hasRole('admin') and hasPermission('todo', 'read')")
public class TodoController {

    final private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    @Operation(summary = "Get all todos")
    public List<Todo> getAllTodos() {
        return todoService.findAll();
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get all todos with pagination")
    public ResponseDto<List<Map<String, Object>>> getAllTodosWithPaginated(
            @SearchableParam(
                    searchFields = {"name", "description", "price"}
            )
            @Parameter(schema = @Schema(nullable = true,implementation = SearchRequest.class),allowEmptyValue = true)
            SearchRequest search,
            @PageableParam
            PaginationRequest paginationRequest,
            @SortableParam(
                    allowedFields = {"name", "price", "createdAt"}
            )
            List<SortRequest> sortRequest
    ) {
        log.info("Page: {}", paginationRequest);
        log.info("Sort: {}", sortRequest);
        log.info("Search: {}", search);

        boolean searchQuery = search.hasSearch();
        log.info("Search query: {}", searchQuery);

        // Mock data
        List<Map<String, Object>> todos = List.of(
                Map.of("id", 1, "title", "Todo 1", "completed", false),
                Map.of("id", 2, "title", "Todo 2", "completed", false),
                Map.of("id", 3, "title", "Todo 3", "completed", false)
        );

        // Calculate pagination with 1-based indexing
        int startIndex = (paginationRequest.getPage() - 1) * paginationRequest.getLimit(); // Convert to 0-based index for calculation
        int endIndex = Math.min(startIndex + paginationRequest.getLimit(), todos.size());
        List<Map<String, Object>> paginatedTodos = startIndex < todos.size()
                ? todos.subList(startIndex, endIndex)
                : Collections.emptyList();

        PageResponse<Map<String, Object>> pageResponse = PageResponse.of(
                paginatedTodos,
                todos.size(),
                paginationRequest.getPage(), // Keep original 1-based page number
                paginationRequest.getLimit()
        );

        return ResponseBuilder.successPage(pageResponse);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get todo by id")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    @Operation(summary = "Create a new todo")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        todoService.create(todo);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a todo")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        Todo existingTodo = todoService.findById(id);
        if (existingTodo == null) {
            return ResponseEntity.notFound().build();
        }
        todo.setId(id);
        todoService.update(todo);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a todo by id")
    public ResponseEntity<Todo> deleteTodoById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }
        todoService.deleteById(id);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/completed")
    @Operation(summary = "Get todos by status")
    public List<Todo> getTodosByStatus(@RequestParam Boolean completed) {
        return todoService.findByCompleted(completed);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update todo status")
    public ResponseEntity<Void> updateTodoStatus(
            @PathVariable Long id,
            @RequestParam Boolean completed) {
        todoService.updateStatus(id, completed);
        return ResponseEntity.ok().build();
    }
}
