package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.domain.Todo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(){
        return new Todo("todo_example1", "example_description", true ,"2020-06-10T15:04:08.756Z");
    }

    public Todo createSecondTodo(){
        return new Todo("todo_example2", "example_description", false ,"2020-06-11T15:04:08.756Z");
    }

    @Test
    public void whenFindByTitleContaining_thenReturnTodo() {
        Todo todo = createTodo();
        entityManager.persist(todo);

        Todo found = todoRepository.findByTitleContaining(todo.getTitle());
        assertThat(found.getTitle()).isEqualTo(todo.getTitle());
    }

    @Test
    public void whenFindByCompleted_thenReturnTodos() {
        Todo todo = createTodo();
        entityManager.persist(todo);
        List<Todo> found = todoRepository.findByCompleted(true);

        assertThat(found).contains(todo);
    }

    @Test
    public void whenFindByUncompleted_thenReturnTodos() {
        Todo todo = createTodo();
        Todo uncompleted_todo= createSecondTodo();
        entityManager.persist(todo);
        entityManager.persist(uncompleted_todo);
        List<String> found = todoRepository.findByUncompleted();
        assertThat(found).contains(uncompleted_todo.getTitle()).doesNotContain(todo.getTitle());
    }
}
