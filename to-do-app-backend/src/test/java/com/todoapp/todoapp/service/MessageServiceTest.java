package com.todoapp.todoapp.service;

import com.todoapp.todoapp.domain.Todo;
import com.todoapp.todoapp.domain.twilio.TwilioClient;
import com.todoapp.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @MockBean
    private TodoRepository todoRepository;

    @MockBean
    private TwilioClient mockTwilioClient;


    public Todo createTodo() {
        return new Todo("todo_example1", "example_description", false, "2020-06-10T15:04:08.756Z");
    }

    @Test
    @DisplayName("Test customMessage")
    public void testCustomMessage() {
        List<String> todos = Arrays.asList(createTodo().getTitle());
        doReturn(todos).when(todoRepository).findByUncompleted();
        String result = messageService.customMessage();
        assertEquals("Hey, you have uncompleted tasks to complete this week: todo_example1", result);
    }
}
