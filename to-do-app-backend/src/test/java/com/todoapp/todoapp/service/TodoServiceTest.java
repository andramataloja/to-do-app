package com.todoapp.todoapp.service;

import com.todoapp.todoapp.domain.Todo;
import com.todoapp.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@SpringBootTest
public class TodoServiceTest {

   @Autowired
    private TodoService todoService;

    @MockBean
    private TodoRepository todoRepository;

    public Todo createTodo(){
       return new Todo("todo_example1", "example_description", false ,"2020-06-10T15:04:08.756Z");
    }

    public List<Todo> createTodoList(){
        Todo todo1 = new Todo("todo_example1", "example_description", false ,"2020-06-10T15:04:08.756Z");
        Todo todo2 = new Todo("todo_example2", "example_description2", true ,"2020-06-10T15:14:08.756Z");

        List<Todo> todos = new ArrayList<>();
        todos.add(todo1);
        todos.add(todo2);

        return todos;
    }

    @Test
    @DisplayName("Test findAll")
    public void testFindAll() {
        doReturn(createTodoList()).when(todoRepository).findAll(Sort.by(Sort.Direction.ASC, "completed", "createdAt"));

        List<Todo> returnedTodos = todoService.getAllTodos();
        assertNotNull(returnedTodos);
        Assertions.assertEquals(2, returnedTodos.size());
    }

    @Test
    @DisplayName("Test findByTitle")
    public void testFindByTitle() {
        Todo todo = createTodo();
        doReturn(todo).when(todoRepository).findByTitleContaining("todo_example1");
        Todo returnedTodo = todoService.getByTitle("todo_example1");

        assertNotNull(returnedTodo);
        Assertions.assertEquals(todo, returnedTodo);
        Assertions.assertEquals(todo.getTitle(), returnedTodo.getTitle());
    }

    @Test
    @DisplayName("Test findById")
    public void testFindById() {
        Todo todo = createTodo();
        doReturn(Optional.of(todo)).when(todoRepository).findById(1l);
        Optional<Todo> returnedTodo = todoService.getTodo(1l);

        Assertions.assertTrue(returnedTodo.isPresent());
        Assertions.assertSame(returnedTodo.get(), todo);
    }

    @Test
    @DisplayName("Test addTodo")
    public void testAddTodo() {
        Todo todo = createTodo();
        doReturn(todo).when(todoRepository).save(any());
        Todo returnedTodo = todoService.addTodo(todo);

        Assertions.assertNotNull(returnedTodo);
        Assertions.assertEquals(todo, returnedTodo);
    }

    @Test
    @DisplayName("Test updateTodo")
    public void testUpdateTodo() {
        Todo todo = createTodo();
        doReturn(todo).when(todoRepository).save(todo);
        Todo returnedTodo = todoService.updateTodo(todo);

        Assertions.assertNotNull(returnedTodo);
        Assertions.assertEquals(todo, returnedTodo);
    }

    @Test
    @DisplayName("Test deleteTodo")
    public void testDeleteTodoById() {
        Todo todo = createTodo();
        todoService.deleteTodo(todo.getId());
        Mockito.verify(todoRepository, times(1)).deleteById(eq(todo.getId()));
    }

    @Test
    @DisplayName("Test findByCompleted")
    public void testFindByCompleted() {
        List <Todo> todos = Arrays.asList(createTodo());
        doReturn(todos).when(todoRepository).findByCompleted(true);
        List<Todo> returnedTodos = todoService.findByCompleted();

        assertNotNull(returnedTodos);
        Assertions.assertEquals(1, returnedTodos.size());
        Assertions.assertEquals(todos, returnedTodos);
    }

    @Test
    @DisplayName("Test findByUncompleted")
    public void testFindByUncompleted() {
        List <Todo> todos = Arrays.asList(createTodo());
        doReturn(todos).when(todoRepository).findByUncompleted();
        List<String> returnedTodos = todoService.findByUncompleted();

        assertNotNull(returnedTodos);
        Assertions.assertEquals(1, returnedTodos.size());
        Assertions.assertEquals(todos, returnedTodos);
    }
}
