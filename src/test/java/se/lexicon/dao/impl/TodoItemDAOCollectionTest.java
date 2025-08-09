package se.lexicon.dao.impl;

import org.junit.jupiter.api.*;
import se.lexicon.dao.impl.TodoItemDAOCollection;
import se.lexicon.model.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TodoItemDAOCollectionTest {

    private TodoItemDAOCollection dao;
    private Person person1;
    private Person person2;
    private AppUser user1;
    private AppUser user2;

    @BeforeEach
    void setUp() {
        // Clear static states to avoid test pollution
        AppUser.clearInstances();
        Person.removeEmail("john@example.com");
        Person.removeEmail("jane@example.com");
        se.lexicon.dao.idSequencer.PersonIdSequencer.getInstance().reset();
        se.lexicon.dao.idSequencer.ToDoItemIdSequencer.getInstance().reset();

        dao = new TodoItemDAOCollection();

        user1 = AppUser.getInstance("johnUser", "password123", AppRole.ROLE_APP_USER);
        user2 = AppUser.getInstance("janeUser", "password456", AppRole.ROLE_APP_USER);

        person1 = Person.createPerson("John", "Doe", "john@example.com", user1);
        person2 = Person.createPerson("Jane", "Smith", "jane@example.com", user2);
    }

    private TodoItem createSampleTodo(Person creator, String title, boolean done, LocalDate deadline) {
        TodoItem item = new TodoItem(title, "Task desc for " + title, deadline, creator);
        item.setDone(done);
        return item;
    }

    @Test
    void persist_shouldSaveTodoItem() {
        TodoItem item = createSampleTodo(person1, "Test Task", false, LocalDate.now().plusDays(5));
        TodoItem saved = dao.persist(item);
        assertNotNull(saved);
        assertEquals(item, saved);
        assertEquals(1, dao.findAll().size());
    }

    @Test
    void persist_nullTodoItem_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> dao.persist(null));
    }

    @Test
    void findById_existingId_shouldReturnTodoItem() {
        TodoItem item = createSampleTodo(person1, "Find Me", false, LocalDate.now().plusDays(10));
        dao.persist(item);
        TodoItem found = dao.findById(item.getId());
        assertNotNull(found);
        assertEquals(item, found);
    }

    @Test
    void findById_nonExistingId_shouldReturnNull() {
        assertNull(dao.findById(9999));
    }

    @Test
    void findById_invalidId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> dao.findById(null));
        assertThrows(IllegalArgumentException.class, () -> dao.findById(0));
        assertThrows(IllegalArgumentException.class, () -> dao.findById(-5));
    }

    @Test
    void findAll_shouldReturnAllPersistedItems() {
        TodoItem item1 = createSampleTodo(person1, "Task 1", false, LocalDate.now().plusDays(3));
        TodoItem item2 = createSampleTodo(person2, "Task 2", true, LocalDate.now().plusDays(7));
        dao.persist(item1);
        dao.persist(item2);

        List<TodoItem> allItems = dao.findAll();
        assertEquals(2, allItems.size());
        assertTrue(allItems.contains(item1));
        assertTrue(allItems.contains(item2));
    }

    @Test
    void findAllByDoneStatus_shouldFilterByDone() {
        TodoItem doneItem = createSampleTodo(person1, "Done Task", true, LocalDate.now().plusDays(1));
        TodoItem notDoneItem = createSampleTodo(person2, "Not Done Task", false, LocalDate.now().plusDays(1));
        dao.persist(doneItem);
        dao.persist(notDoneItem);

        List<TodoItem> doneList = dao.findAllByDoneStatus(true);
        assertEquals(1, doneList.size());
        assertEquals(doneItem, doneList.get(0));

        List<TodoItem> notDoneList = dao.findAllByDoneStatus(false);
        assertEquals(1, notDoneList.size());
        assertEquals(notDoneItem, notDoneList.get(0));
    }

    @Test
    void findByTitleContains_shouldFindCaseInsensitive() {
        TodoItem item = createSampleTodo(person1, "Special Title Task", false, LocalDate.now().plusDays(2));
        dao.persist(item);

        List<TodoItem> found = dao.findByTitleContains("special");
        assertFalse(found.isEmpty());
        assertEquals(item, found.get(0));

        found = dao.findByTitleContains("TITLE");
        assertFalse(found.isEmpty());
        assertEquals(item, found.get(0));
    }

    @Test
    void findByTitleContains_nullOrEmpty_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> dao.findByTitleContains(null));
        assertThrows(IllegalArgumentException.class, () -> dao.findByTitleContains(""));
        assertThrows(IllegalArgumentException.class, () -> dao.findByTitleContains("  "));
    }

    @Test
    void findByPersonId_shouldReturnItemsByPerson() {
        TodoItem item1 = createSampleTodo(person1, "Person1 Task", false, LocalDate.now().plusDays(5));
        TodoItem item2 = createSampleTodo(person2, "Person2 Task", false, LocalDate.now().plusDays(5));
        dao.persist(item1);
        dao.persist(item2);

        List<TodoItem> listPerson1 = dao.findByPersonId(person1.getId());
        assertEquals(1, listPerson1.size());
        assertEquals(item1, listPerson1.get(0));

        List<TodoItem> listPerson2 = dao.findByPersonId(person2.getId());
        assertEquals(1, listPerson2.size());
        assertEquals(item2, listPerson2.get(0));
    }

    @Test
    void findByPersonId_invalidId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> dao.findByPersonId(null));
        assertThrows(IllegalArgumentException.class, () -> dao.findByPersonId(0));
        assertThrows(IllegalArgumentException.class, () -> dao.findByPersonId(-5));
    }

    @Test
    void findByDeadlineBefore_shouldReturnCorrectItems() {
        TodoItem early = createSampleTodo(person1, "Early Task", false, LocalDate.now().plusDays(1));
        TodoItem late = createSampleTodo(person2, "Late Task", false, LocalDate.now().plusDays(10));
        dao.persist(early);
        dao.persist(late);

        List<TodoItem> beforeDate = dao.findByDeadlineBefore(LocalDate.now().plusDays(5));
        assertEquals(1, beforeDate.size());
        assertEquals(early, beforeDate.get(0));
    }

    @Test
    void findByDeadlineAfter_shouldReturnCorrectItems() {
        TodoItem early = createSampleTodo(person1, "Early Task", false, LocalDate.now().plusDays(1));
        TodoItem late = createSampleTodo(person2, "Late Task", false, LocalDate.now().plusDays(10));
        dao.persist(early);
        dao.persist(late);

        List<TodoItem> afterDate = dao.findByDeadlineAfter(LocalDate.now().plusDays(5));
        assertEquals(1, afterDate.size());
        assertEquals(late, afterDate.get(0));
    }

    @Test
    void remove_existingId_shouldRemoveItem() {
        TodoItem item = createSampleTodo(person1, "To Remove", false, LocalDate.now().plusDays(4));
        dao.persist(item);

        dao.remove(item.getId());
        assertTrue(dao.findAll().isEmpty());
        assertNull(dao.findById(item.getId()));
    }

    @Test
    void remove_nonExistingId_shouldDoNothing() {
        dao.remove(9999); // No exception expected
    }

    @Test
    void remove_invalidId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> dao.remove(null));
        assertThrows(IllegalArgumentException.class, () -> dao.remove(0));
        assertThrows(IllegalArgumentException.class, () -> dao.remove(-10));
    }
}

