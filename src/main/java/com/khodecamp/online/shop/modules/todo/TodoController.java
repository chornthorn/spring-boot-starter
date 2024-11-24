package com.khodecamp.online.shop.modules.todo;

import com.khodecamp.online.shop.core.annotation.PageableParam;
import com.khodecamp.online.shop.core.annotation.SearchableParam;
import com.khodecamp.online.shop.core.annotation.SortableParam;
import com.khodecamp.online.shop.core.request.PageRequest;
import com.khodecamp.online.shop.core.request.PageResult;
import com.khodecamp.online.shop.core.request.SortRequest;
import com.khodecamp.online.shop.core.response.ResponseBuilder;
import com.khodecamp.online.shop.core.response.ResponseDto;
import com.khodecamp.online.shop.core.request.SearchRequest;
import com.khodecamp.online.shop.modules.todo.model.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@Slf4j
public class TodoController {

    final private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.findAll();
    }

    @GetMapping("/paginated")
    public ResponseDto<List<Map<String, Object>>> getAllTodosWithPaginated(
            @SearchableParam(
                    searchFields = {"name", "description", "price"}
            )
            SearchRequest search,
            @PageableParam
            PageRequest pageRequest,
            @SortableParam(
                    allowedFields = {"name", "price", "createdAt"}
            )
            List<SortRequest> sortRequest
    ) {
        log.info("Page: {}", pageRequest);
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
        int startIndex = (pageRequest.getPage() - 1) * pageRequest.getLimit(); // Convert to 0-based index for calculation
        int endIndex = Math.min(startIndex + pageRequest.getLimit(), todos.size());
        List<Map<String, Object>> paginatedTodos = startIndex < todos.size()
                ? todos.subList(startIndex, endIndex)
                : Collections.emptyList();

        PageResult<Map<String, Object>> pageResult = PageResult.of(
                paginatedTodos,
                todos.size(),
                pageRequest.getPage(), // Keep original 1-based page number
                pageRequest.getLimit()
        );

        return ResponseBuilder.successPage(pageResult);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        todoService.create(todo);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<Todo> deleteTodoById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }
        todoService.deleteById(id);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/completed")
    public List<Todo> getTodosByStatus(@RequestParam Boolean completed) {
        return todoService.findByCompleted(completed);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateTodoStatus(
            @PathVariable Long id,
            @RequestParam Boolean completed) {
        todoService.updateStatus(id, completed);
        return ResponseEntity.ok().build();
    }
}
