package com.khodecamp.online.shop.service;

import com.khodecamp.online.shop.mapper.TodoMapper;
import com.khodecamp.online.shop.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {
    final private TodoMapper todoMapper;

    @Autowired
    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public List<Todo> findAll() {
        return todoMapper.findAll();
    }

    public Todo findById(Long id) {
        return todoMapper.findById(id);
    }

    public void create(Todo todo) {
        todoMapper.insert(todo);
    }

    public void update(Todo todo) {
        todoMapper.update(todo);
    }

    public void deleteById(Long id) {
        todoMapper.delete(id);
    }

    public List<Todo> findByCompleted(Boolean isCompleted) {
        return todoMapper.findByCompleted(isCompleted);
    }

    @Transactional
    public void updateStatus(Long id, Boolean isCompleted) {
        todoMapper.updateStatus(id, isCompleted);
    }
}
