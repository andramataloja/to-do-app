package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  TodoRepository extends JpaRepository<Todo, Long> {
    Todo findByTitleContaining(String title);
    List<Todo> findByCompleted(boolean completed);


    @Query("select t.title from Todo t where t.completed = false")
    List<String> findByUncompleted();
}
