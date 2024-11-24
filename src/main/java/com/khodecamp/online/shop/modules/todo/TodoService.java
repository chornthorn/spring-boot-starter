package com.khodecamp.online.shop.modules.todo;

import com.khodecamp.online.shop.core.annotation.PageableParam;
import com.khodecamp.online.shop.core.request.PageRequest;
import com.khodecamp.online.shop.modules.todo.mapper.TodoMapper;
import com.khodecamp.online.shop.modules.todo.model.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TodoService {
    final private TodoMapper todoMapper;

    @Autowired
    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public List<Todo> findAll() {
        return todoMapper.findAll();
    }

    public List<Todo> getAllTodosWithPaginated(
            @PageableParam PageRequest pageRequest
    ) {
        log.info("Page: {}, Limit: {}", pageRequest.getPage(), pageRequest.getLimit());
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
