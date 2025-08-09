package se.lexicon.model;

import org.junit.jupiter.api.*;
import se.lexicon.dao.idSequencer.PersonIdSequencer;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoItemTest {

    private Person creator;
    private TodoItem todoItem;
    private AppUser appUser;

    @BeforeAll
    public void beforeAll() {
        PersonIdSequencer.getInstance().reset();
        AppUser.clearInstances();
        appUser = AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER);
    }

    @BeforeEach
    public void setup() {
        // create new Person for each test
        creator = Person.createPerson("Lina", "Katt", "lina@example.se", appUser);

        todoItem = new TodoItem("Write Tests", "Write unit tests for TodoItem class",
                LocalDate.now().plusDays(5), creator);
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
    @DisplayName("Create TodoItem successfully with valid inputs")
    public void createTodoItemValidInputsSuccess() {
        assertNotNull(todoItem);
        assertEquals("Write Tests", todoItem.getTitle());
        assertEquals("Write unit tests for TodoItem class", todoItem.getTaskDescription());
        assertEquals(LocalDate.now().plusDays(5), todoItem.getDeadLine());
        assertEquals(creator, todoItem.getCreator());
        assertFalse(todoItem.isDone());
        assertTrue(todoItem.getId() > 0);
    }

    @Test
    @DisplayName("Should change the title of the todo item")
    public void setTitleUpdatesCorrectly() {
        todoItem.setTitle("New Title");
        assertEquals("New Title", todoItem.getTitle());
    }

    @Test
    @DisplayName("Should change the task description of the todo item")
    public void setTaskDescriptionUpdatesCorrectly() {
        todoItem.setTaskDescription("New Description");
        assertEquals("New Description", todoItem.getTaskDescription());
    }

    @Test
    @DisplayName("Should change the deadline of the todo item")
    public void setDeadlineUpdatesCorrectly() {
        LocalDate newDeadline = LocalDate.now().plusDays(10);
        todoItem.setDeadLine(newDeadline);
        assertEquals(newDeadline, todoItem.getDeadLine());
    }

    @Test
    @DisplayName("Should mark todo item as done")
    public void setDoneUpdatesCorrectly() {
        todoItem.setDone(true);
        assertTrue(todoItem.isDone());
    }

    @Test
    @DisplayName("Should change the creator of the todo item")
    public void setCreatorUpdatesCorrectly() {
        Person newCreator = Person.createPerson("Anna", "Smith", "anna@example.se", appUser);
        todoItem.setCreator(newCreator);
        assertEquals(newCreator, todoItem.getCreator());
        Person.removeEmail("anna@example.se");
    }

    @Test
    @DisplayName("Creating TodoItem with null title throws exception")
    public void createTodoItemNullTitleThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem(null, "desc", LocalDate.now().plusDays(1), creator);
        });
        assertTrue(ex.getMessage().contains("Title cannot be null or empty"));
    }

    @Test
    @DisplayName("Creating TodoItem with null description throws exception")
    public void createTodoItemNullDescriptionThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem("Title", null, LocalDate.now().plusDays(1), creator);
        });
        assertTrue(ex.getMessage().contains("Title cannot be null or empty"));
    }

    @Test
    @DisplayName("Creating TodoItem with null deadline throws exception")
    public void createTodoItemNullDeadlineThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem("Title", "desc", null, creator);
        });
        assertTrue(ex.getMessage().contains("Date cannot be left empty"));
    }

    @Test
    @DisplayName("Creating TodoItem with past deadline throws exception")
    public void createTodoItemPastDeadlineThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem("Title", "desc", LocalDate.now().minusDays(1), creator);
        });
        assertTrue(ex.getMessage().contains("The task is overdue"));
    }

    @Test
    @DisplayName("Creating TodoItem with null creator throws exception")
    public void createTodoItemNullCreatorThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem("Title", "desc", LocalDate.now().plusDays(1), null);
        });
        assertTrue(ex.getMessage().contains("Creator cannot be null"));
    }

    @Test
    @DisplayName("isOverdue returns false when deadline is today or future")
    public void isOverdueFalseWhenNotPast() {
        todoItem.setDeadLine(LocalDate.now().plusDays(1));
        assertFalse(todoItem.isOverdue());
        todoItem.setDeadLine(LocalDate.now());
        assertFalse(todoItem.isOverdue());
    }

    @Test
    @DisplayName("isOverdue returns true when deadline is past")
    public void isOverdueTrueWhenPast() throws Exception {
        // Use reflection to set deadline field to a past date
        Field deadlineField = TodoItem.class.getDeclaredField("deadLine");
        deadlineField.setAccessible(true);
        deadlineField.set(todoItem, LocalDate.now().minusDays(1));

        assertTrue(todoItem.isOverdue());
    }

    @Test
    @DisplayName("getOverdueInfo returns correct string")
    public void getOverdueInfoReturnsCorrectString() {
        todoItem.setDone(true);
        String info = todoItem.getOverdueInfo();
        assertTrue(info.contains("Job: Write Tests"));
        assertTrue(info.contains("Id number: " + todoItem.getId()));
        assertTrue(info.contains("Overdue: Yes"));
    }

    @Test
    @DisplayName("toString returns correct string representation")
    public void toStringReturnsCorrectString() {
        String str = todoItem.toString();
        assertTrue(str.contains("Id number: " + todoItem.getId()));
        assertTrue(str.contains("Job : Write Tests"));
        assertTrue(str.contains("Job description: Write unit tests for TodoItem class"));
        assertTrue(str.contains("Due date: " + todoItem.getDeadLine().toString()));
        assertTrue(str.contains("Done: No"));
        assertTrue(str.contains("Customer Lina"));
    }

    @Test
    @DisplayName("Equals returns true for same object")
    public void equalsSameObjectReturnsTrue() {
        assertEquals(todoItem, todoItem);
    }

    @Test
    @DisplayName("Equals returns false for different objects with different ids")
    public void equalsDifferentObjectsReturnFalse() {
        TodoItem another = new TodoItem("Another", "desc", LocalDate.now().plusDays(2), creator);
        assertNotEquals(todoItem, another);
    }

    @Test
    @DisplayName("HashCode is consistent for same object")
    public void hashCodeConsistentForSameObject() {
        int hash1 = todoItem.hashCode();
        int hash2 = todoItem.hashCode();
        assertEquals(hash1, hash2);
    }
}
