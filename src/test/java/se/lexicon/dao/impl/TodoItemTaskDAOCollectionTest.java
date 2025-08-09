package se.lexicon.dao.impl;

import org.junit.jupiter.api.*;
import se.lexicon.dao.impl.TodoItemTaskDAOCollection;
import se.lexicon.model.AppRole;
import se.lexicon.model.AppUser;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import se.lexicon.model.TodoItemTask;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)

class TodoItemTaskDAOCollectionTest {

    private TodoItemTaskDAOCollection dao;
    private Person person1;
    private Person person2;
    private TodoItem todoItem1;
    private TodoItem todoItem2;
    private TodoItemTask task1;
    private TodoItemTask task2;

    @BeforeEach
    void setUp() {
        // Clear static states to avoid test pollution
        AppUser.clearInstances();
        Person.removeEmail("john@example.com");
        Person.removeEmail("jane@example.com");
        se.lexicon.dao.idSequencer.PersonIdSequencer.getInstance().reset();
        se.lexicon.dao.idSequencer.ToDoItemIdSequencer.getInstance().reset();

        dao = new TodoItemTaskDAOCollection();

        // Create roles
        AppRole userRole = AppRole.ROLE_APP_USER;

        // Create AppUsers using the factory method
        AppUser user1 = AppUser.getInstance("user1", "pass1", userRole);
        AppUser user2 = AppUser.getInstance("user2", "pass2", userRole);

        // Create Persons with AppUsers
        person1 = Person.createPerson("John", "Doe", "john@example.com", user1);
        person2 = Person.createPerson("Jane", "Smith", "jane@example.com", user2);


        // Create TodoItems with valid deadline and creator
        todoItem1 = new TodoItem("Task One", "Description One", LocalDate.now().plusDays(7), person1);
        todoItem2 = new TodoItem("Task Two", "Description Two", LocalDate.now().plusDays(10), person2);


        // Create TodoItemTasks
        task1 = new TodoItemTask(todoItem1, person1); // assigned
        task2 = new TodoItemTask(todoItem2, null);    // unassigned
    }






@Test
    @DisplayName("Persist valid TodoItemTask")
    void persistValidTodoItemTask() {
        TodoItemTask persisted = dao.persist(task1);
        assertNotNull(persisted);
        assertEquals(task1.getId(), persisted.getId());
        assertTrue(persisted.isAssigned());
    }

    @Test
    @DisplayName("Persist null TodoItemTask throws exception")
    void persistNullTodoItemTaskThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> dao.persist(null));
    }

    @Test
    @DisplayName("Find by valid ID returns correct task")
    void findByIdValid() {
        dao.persist(task1);
        TodoItemTask found = dao.findById(task1.getId());
        assertEquals(task1, found);
    }

    @Test
    @DisplayName("Find by non-existing ID returns null")
    void findByIdNotFound() {
        assertNull(dao.findById(9999));
    }

    @Test
    @DisplayName("Find by null or negative ID throws exception")
    void findByIdInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> dao.findById(null));
        assertThrows(IllegalArgumentException.class, () -> dao.findById(0));
        assertThrows(IllegalArgumentException.class, () -> dao.findById(-5));
    }

    @Test
    @DisplayName("Find all returns all persisted tasks")
    void findAllReturnsAll() {
        dao.persist(task1);
        dao.persist(task2);
        List<TodoItemTask> all = dao.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(task1));
        assertTrue(all.contains(task2));
    }

    @Test
    @DisplayName("Find by assigned status returns correct list")
    void findByAssignedStatus() {
        dao.persist(task1); // assigned
        dao.persist(task2); // unassigned

        List<TodoItemTask> assigned = dao.findByAssignedStatus(true);
        List<TodoItemTask> unassigned = dao.findByAssignedStatus(false);

        assertEquals(1, assigned.size());
        assertTrue(assigned.contains(task1));
        assertEquals(1, unassigned.size());
        assertTrue(unassigned.contains(task2));
    }

    @Test
    @DisplayName("Find by person ID returns only that person's tasks")
    void findByPersonIdValid() {
        dao.persist(task1);
        dao.persist(task2);

        List<TodoItemTask> found = dao.findByPersonId(person1.getId());
        assertEquals(1, found.size());
        assertTrue(found.contains(task1));
    }

    @Test
    @DisplayName("Find by person ID when none match returns empty list")
    void findByPersonIdNoMatches() {
        dao.persist(task2); // unassigned
        List<TodoItemTask> found = dao.findByPersonId(person1.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Find by person ID null or negative throws exception")
    void findByPersonIdInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> dao.findByPersonId(null));
        assertThrows(IllegalArgumentException.class, () -> dao.findByPersonId(0));
        assertThrows(IllegalArgumentException.class, () -> dao.findByPersonId(-3));
    }

    @Test
    @DisplayName("Remove existing task by ID")
    void removeValidId() {
        dao.persist(task1);
        dao.remove(task1.getId());
        assertNull(dao.findById(task1.getId()));
    }

    @Test
    @DisplayName("Remove non-existing task does not affect list")
    void removeNotFoundId() {
        dao.persist(task1);
        dao.remove(9999);
        assertEquals(1, dao.findAll().size());
    }

    @Test
    @DisplayName("Remove null or negative ID throws exception")
    void removeInvalidIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> dao.remove(null));
        assertThrows(IllegalArgumentException.class, () -> dao.remove(0));
        assertThrows(IllegalArgumentException.class, () -> dao.remove(-10));
    }
}

