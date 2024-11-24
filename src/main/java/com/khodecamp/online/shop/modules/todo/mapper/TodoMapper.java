package com.khodecamp.online.shop.modules.todo.mapper;

import com.khodecamp.online.shop.modules.todo.model.Todo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodoMapper {

    List<Todo> findAll();

    Todo findById(@Param("id") Long id);
    void insert(Todo todo);
    void update(Todo todo);
    void delete(@Param("id") Long id);
    List<Todo> search(@Param("searchTerm") String searchTerm);
    List<Todo> findByCompleted(@Param("isCompleted") Boolean isCompleted);
    void updateStatus(@Param("id") Long id, @Param("isCompleted") Boolean isCompleted);
}
