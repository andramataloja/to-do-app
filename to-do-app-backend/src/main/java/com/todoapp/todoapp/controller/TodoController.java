package com.todoapp.todoapp.controller;

import com.todoapp.todoapp.model.Todo;
import com.todoapp.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
@RestController
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAllTodos(@RequestParam(required = false) String title) {
        try {
            List<Todo> todos = new ArrayList<>();
            if (title == null)
                todoRepository.findAll().forEach(todos::add);
            else
                todoRepository.findByTitleContaining(title).forEach(todos::add);
            if (todos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/todos/{id}")
    public ResponseEntity<Object> getTodoById(@PathVariable long id){
        Optional<Todo> todoData = todoRepository.findById(id);

        if (todoData.isPresent()) {
            return new ResponseEntity<>(todoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/todos")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        try {
            Todo _todo = todoRepository.save(new Todo(todo.getTitle(), todo.getDescription(), false));
            return new ResponseEntity<>(_todo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable long id, @RequestBody Todo todo) {
        Optional<Todo> todoData = todoRepository.findById(id);

        if (todoData.isPresent()) {
            Todo _todo = todoData.get();
            _todo.setTitle(todo.getTitle());
            _todo.setDescription(todo.getDescription());
            _todo.setCompleted(todo.isCompleted());
            return new ResponseEntity<>(todoRepository.save(_todo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable long id) {
        try {
            todoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/todos/completed")
    public ResponseEntity<List<Todo>> findByCompleted() {
        try {
            List<Todo> todos = todoRepository.findByCompleted(true);

            if (todos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/todos/uncompleted")
    public ResponseEntity<List<Todo>> findByUnCompleted() {
        try {
            List<Todo> todos = todoRepository.findByCompleted(false);

            if (todos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
