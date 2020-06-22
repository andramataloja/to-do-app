package com.todoapp.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.todoapp.domain.Todo;
import com.todoapp.todoapp.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService todoService;

    private ObjectMapper objectMapper = new ObjectMapper();


    public Todo createTodo(){
        return new Todo("todo_example1", "example_description", false ,"2020-06-10T15:04:08.756Z");
    }

    @Test
    public void givenTodos_whenGetAllTodos_thenReturnJsonArray() throws Exception {
        Todo todo = createTodo();
        given(todoService.getAllTodos()).willReturn(Arrays.asList(todo));

        mvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(todo.getTitle())));
    }

    @Test
    public void givenTodos_whenGetTodoById_thenReturnJsonArray() throws Exception {
        Todo todo = createTodo();
        given(todoService.getTodo(todo.getId())).willReturn(Optional.of(todo));

        mvc.perform(get("/api/todos/{id}",todo.getId() )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(todo.getTitle())));
    }

    @Test
    public void givenTodos_whenGetTodoByCompleted_thenReturnJsonArray() throws Exception {
        Todo todo = createTodo();
        given(todoService.findByCompleted()).willReturn(Arrays.asList(todo));

        mvc.perform(get("/api/todos/completed")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(todo.getTitle())));
    }

    @Test
    public void givenTodos_whenGetTodoByUncompleted_thenReturnJsonArray() throws Exception {
        String todo = createTodo().getTitle();
        given(todoService.findByUncompleted()).willReturn(Arrays.asList(todo));

        mvc.perform(get("/api/todos/uncompleted")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0]", is(todo)));
    }

    @Test
    public void saveTodo_itShouldReturnStatusCreated() throws Exception {
        Todo todo = createTodo();
        given(todoService.addTodo(any(Todo.class))).willReturn(todo);

        String jsonString = objectMapper.writeValueAsString(todo);

        mvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTodo_itShouldReturnStatusOk() throws Exception {
        Todo todo = createTodo();
        given(todoService.getTodo(todo.getId())).willReturn(Optional.of(todo));

        String jsonString = objectMapper.writeValueAsString(todo);

        mvc.perform(put("/api/todos/{id}", todo.getId())
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTodoById_itShouldReturnStatusNoContent() throws Exception {
        Todo todo = createTodo();

        Mockito.doNothing().when(todoService).deleteTodo(any(Long.class));

        mvc.perform(delete("/api//todos/{id}", todo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
