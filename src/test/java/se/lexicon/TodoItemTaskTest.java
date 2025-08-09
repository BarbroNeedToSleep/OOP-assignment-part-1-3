package se.lexicon;

import org.junit.jupiter.api.*;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import se.lexicon.model.TodoItemTask;
import se.lexicon.model.AppUser;
import se.lexicon.model.AppRole;
import se.lexicon.dao.idSequencer.ToDoItemTaskIdSequencer;
import se.lexicon.dao.idSequencer.PersonIdSequencer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoItemTaskTest {

    private TodoItem todoItem;
    private Person assignee;
    private AppUser appUser;

    @BeforeAll
    public void beforeAll() {
        PersonIdSequencer.getInstance().reset();
        ToDoItemTaskIdSequencer.getInstance().reset();
        AppUser.clearInstances();
        appUser = AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER);
    }

    @BeforeEach
    public void setup() {
        assignee = Person.createPerson("Lina", "Katt", "lina@example.se", appUser);
        todoItem = new TodoItem("Write Tests", "Write unit tests for TodoItemTask class",
                LocalDate.now().plusDays(5), assignee);
    }

    @AfterEach
    public void tearDown() {
        Person.removeEmail("lina@example.se");
    }

    @AfterAll
    public void afterAll() {
        AppUser.clearInstances();
    }

    @Test
    @DisplayName("Create TodoItemTask successfully with valid inputs and assigned")
    public void createTodoItemTaskValidInputsAssigned() {
        TodoItemTask task = new TodoItemTask(todoItem, assignee);
        assertNotNull(task);
        assertEquals(todoItem, task.getTodoItem());
        assertEquals(assignee, task.getAssignee());
        assertTrue(task.isAssigned());
        assertTrue(task.getId() > 0);
    }

    @Test
    @DisplayName("Create TodoItemTask successfully with null assignee and assigned false")
    public void createTodoItemTaskNullAssignee() {
        TodoItemTask task = new TodoItemTask(todoItem, null);
        assertNotNull(task);
        assertEquals(todoItem, task.getTodoItem());
        assertNull(task.getAssignee());
        assertFalse(task.isAssigned());
    }

    @Test
    @DisplayName("Constructor throws IllegalArgumentException when TodoItem is null")
    public void constructorThrowsWhenTodoItemNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItemTask(null, assignee);
        });
        assertEquals("TodoItem cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("setAssignee updates assignee and assigned correctly")
    public void setAssigneeUpdatesCorrectly() {
        TodoItemTask task = new TodoItemTask(todoItem, null);
        assertFalse(task.isAssigned());

        task.setAssignee(assignee);
        assertEquals(assignee, task.getAssignee());
        assertTrue(task.isAssigned());

        task.setAssignee(null);
        assertNull(task.getAssignee());
        assertFalse(task.isAssigned());
    }

    @Test
    @DisplayName("toString contains key information")
    public void toStringContainsInfo() {
        TodoItemTask task = new TodoItemTask(todoItem, assignee);
        String str = task.toString();
        assertTrue(str.contains("Task ID: " + task.getId()));
        assertTrue(str.contains(todoItem.toString()));
        assertTrue(str.contains(assignee.getFirstName()));
    }

    @Test
    @DisplayName("equals and hashCode behave correctly")
    public void equalsAndHashCode() {
        TodoItemTask task1 = new TodoItemTask(todoItem, assignee);
        TodoItemTask task2 = new TodoItemTask(todoItem, assignee);

        // Different IDs so not equal
        assertNotEquals(task1, task2);
        assertNotEquals(task1.hashCode(), task2.hashCode());

        // equals same object
        assertEquals(task1, task1);
        assertEquals(task1.hashCode(), task1.hashCode());

        // equals null and other class
        assertNotEquals(task1, null);
        assertNotEquals(task1, new Object());
    }
}

