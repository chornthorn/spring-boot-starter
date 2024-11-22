package com.khodecamp.online.shop.controller;

import com.khodecamp.online.shop.model.Todo;
import com.khodecamp.online.shop.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
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
