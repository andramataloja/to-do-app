package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTitleContaining(String title);
    List<Todo> findByCompleted(boolean completed);
}
