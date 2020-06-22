package com.todoapp.todoapp.service;


import com.todoapp.todoapp.domain.Todo;
import com.todoapp.todoapp.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos(){
        return todoRepository.findAll(Sort.by(Sort.Direction.ASC, "completed", "createdAt"));
    }
    public Todo getByTitle(String title){
        return todoRepository.findByTitleContaining(title);
    }
    public Optional<Todo> getTodo(Long id){
        return todoRepository.findById(id);
    }
    public Todo addTodo(Todo todo){
        return todoRepository.save(new Todo(todo.getTitle(), todo.getDescription(), false, todo.getCreatedAt()));
    }
    public Todo updateTodo(Todo todo){
        return todoRepository.save(todo);
    }
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
    public List<Todo> findByCompleted(){
        return todoRepository.findByCompleted(true);
    }
    public List<String> findByUncompleted(){
        return todoRepository.findByUncompleted();
    }
}
